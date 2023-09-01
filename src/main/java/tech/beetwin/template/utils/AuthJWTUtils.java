package tech.beetwin.template.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import tech.beetwin.template.security.UserDetailsImpl;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AuthJWTUtils extends AbstractJwtUtils {
    @Value("${jwt.validity.auth-token}")
    public long jwtTokenValidity;

    private final Logger logger = LoggerFactory.getLogger(AuthJWTUtils.class);

    @Value("${jwt.secret.auth-token}")
    private String secret;

    public String getSubjectFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public List<?> getClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("roles", List.class);
    }

    @Override
    public String getSecret() {
        return secret;
    }

    public AuthJWTUtils setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    @Override
    public long getDuration() {
        return jwtTokenValidity;
    }

    public String generateToken(UserDetailsImpl userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet()));

        return super.generateToken(userDetails.getUsername(), claims);
    }

    public AuthJWTUtils setJwtTokenValidity(long jwtTokenValidity) {
        this.jwtTokenValidity = jwtTokenValidity;
        return this;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
