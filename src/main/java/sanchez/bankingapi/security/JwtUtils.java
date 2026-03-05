package sanchez.bankingapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private final static Logger log = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpirationMs;

    public String generateJwtToken(String username) throws JwtException
    {
        log.info("Generating JWT token for username: {}", username);

        JwtBuilder jwtBuilder = Jwts.builder();

        jwtBuilder.setSubject(username);
        jwtBuilder.setIssuedAt(new Date(System.currentTimeMillis()));
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs));

        Key key = Keys
                .hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        jwtBuilder.signWith(key);

        return jwtBuilder.compact();
    }
    
    public String getUsernameFromJwtToken(String token)
    {
        log.info("Getting username from token");

        JwtParser parser = Jwts
                .parser()
                .setSigningKey(getSigningKey())
                .build();
        Jws<Claims> claimsJws = parser.parseClaimsJws(token);
        return claimsJws.getBody().getSubject();
    }

    public boolean validateJwtToken(String token)
    {
        log.info("Validating JWT token...");
        try {
            JwtParser parser = Jwts
                    .parser()
                    .setSigningKey(getSigningKey())
                    .build();
            Jws<Claims> claimsJws = parser.parseClaimsJws(token);

            Date expiration = claimsJws.getBody().getExpiration();
            if (expiration.before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Key getSigningKey()
    {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}
