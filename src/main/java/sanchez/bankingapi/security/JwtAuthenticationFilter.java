package sanchez.bankingapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final static Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private JwtUtils jwtUtils;

    private BankUserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtUtils jwtUtils, BankUserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Called doFilterInternal from JwtAuthenticationFilter");

        String requestHeader = request.getHeader("Authorization");
        if (!(requestHeader != null && requestHeader.startsWith("Bearer "))) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = requestHeader.substring(7);
        if (jwtToken.isEmpty() || !jwtUtils.validateJwtToken(jwtToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtils.getUsernameFromJwtToken(jwtToken);

        try {
            UserDetails user = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Authentication Success");
        } catch (UsernameNotFoundException e) {
            log.error("Failed to load user by username: {}", username);
        }

        filterChain.doFilter(request, response);
    }
}
