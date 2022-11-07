package tech.zdrzalik.courses.utils;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
public class JWTUtils {

    //TODO przenieść wartości secretu i expiration time
    private static String accessTokenSecret = "123";

    private static int accessTokenExpirationTime = 10000;

    public static String generateAuthenticationToken(String username, Date expiresAt, String issuer, List<String> claims) {
        return Jwts.builder().setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + accessTokenExpirationTime))
                .signWith(SignatureAlgorithm.HS512,accessTokenSecret)
                .compact();
    }

    public Boolean validateAuthenticationToken(String token){
        return false;
    }

}
