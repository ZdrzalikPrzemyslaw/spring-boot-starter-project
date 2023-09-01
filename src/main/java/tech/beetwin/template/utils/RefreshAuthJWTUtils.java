package tech.beetwin.template.utils;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tech.beetwin.template.security.UserDetailsImpl;

import java.util.HashMap;

@Component
public class RefreshAuthJWTUtils extends AbstractJwtUtils {
    @Value("${jwt.validity.refresh-token}")
    public long jwtTokenValidity;

    private final Logger logger = LoggerFactory.getLogger(RefreshAuthJWTUtils.class);

    @Value("${jwt.secret.refresh-token}")
    private String secret;

    public String getSubjectFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    @Override
    public String getSecret() {
        return secret;
    }

    public RefreshAuthJWTUtils setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    @Override
    public long getDuration() {
        return jwtTokenValidity;
    }

    public String generateToken(UserDetailsImpl userDetails) {
        return super.generateToken(userDetails.getUsername(), new HashMap<>());
    }

    public RefreshAuthJWTUtils setJwtTokenValidity(long jwtTokenValidity) {
        this.jwtTokenValidity = jwtTokenValidity;
        return this;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
