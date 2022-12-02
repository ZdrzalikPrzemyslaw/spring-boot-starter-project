package tech.beetwin.stereoscopy.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractJwtUtils implements IJWTUtils {

    @Override
    public String generateToken(@Nullable String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + getDuration()))
                .signWith(SignatureAlgorithm.HS512, getSecret()).compact();
    }

    public Boolean isTokenExpired(String token) {
        try {
            getExpirationDateFromToken(token);
        }catch (ExpiredJwtException e){
            return true;
        }
        return false;
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(getSecret()).parseClaimsJws(token).getBody();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getSecret()).parseClaimsJws(token);
        }catch (MalformedJwtException | ExpiredJwtException | SignatureException e){
            return false;
        }
        return true;

    }
}
