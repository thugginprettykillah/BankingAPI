package sanchez.bankingapi.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sanchez.bankingapi.user.UserEntity;
import sanchez.bankingapi.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankUserDetailsService implements UserDetailsService {

    private final static Logger log = LoggerFactory.getLogger(BankUserDetailsService.class);

    private final UserRepository repository;

    @Autowired
    public BankUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        log.info("Called loadUserByUsername from BankUserDetailsService for username: {}", username);

        UserEntity user = repository
                .findByEmailWithRoles(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(
                        roleEntity -> new SimpleGrantedAuthority(roleEntity.getName().name())
                ).collect(Collectors.toList());

        return new User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
