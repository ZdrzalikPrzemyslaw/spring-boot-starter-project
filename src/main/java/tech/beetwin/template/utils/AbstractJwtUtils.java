package tech.beetwin.template.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.Map;

public abstract class AbstractJwtUtils implements IJWTUtils {

    @Override
    public String generateToken(@Nullable String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + getDuration()))
                .signWith(SignatureAlgorithm.HS512, getSecret()).compact();
    }

    public Boolean isTokenExpired(String token) {
        try {
            getAllClaimsFromToken(token);
        } catch (ExpiredJwtException e) {
            return true;
        }
        return false;
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(getSecret()).parseClaimsJws(token).getBody();
    }

    protected abstract Logger getLogger();

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getSecret()).parseClaimsJws(token);
        } catch (MalformedJwtException | ExpiredJwtException | SignatureException | UnsupportedJwtException e) {
            getLogger().debug("Validate token failed with exception", e);
            return false;
        }
        return true;

    }
}
