package tech.beetwin.stereoscopy.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import tech.beetwin.stereoscopy.model.AccessLevel.AccessLevelsEntity;
import tech.beetwin.stereoscopy.security.UserDetailsImpl;

import java.util.Collections;
import java.util.Objects;

public class AuthJWTUtilsTest {

    private AuthJWTUtils jwtUtils;
    UserDetailsImpl userDetails;
    private String subject = "test@user.com";

    @BeforeEach
    void beforeEach() {
        jwtUtils = new AuthJWTUtils().setSecret("secret1").setJwtTokenValidity(180000);
        userDetails = new UserDetailsImpl(Collections.singleton(new SimpleGrantedAuthority("user")),subject,"Password123","Jan","Nowak",true,true,0L);

    }

    @Test
    void testCorrect() {
        var token = jwtUtils.generateToken(userDetails);
        Assertions.assertTrue(jwtUtils.validateToken(token));
        Assertions.assertEquals(subject,jwtUtils.getSubjectFromToken(token));
        Assertions.assertTrue(jwtUtils.getClaims(token).contains("user"));

    }

    @Test
    void testIncorrect() {
        var token = jwtUtils.generateToken(userDetails);
        Assertions.assertTrue(jwtUtils.validateToken(token));
        Assertions.assertNotEquals("another@user.com",jwtUtils.getSubjectFromToken(token));
        Assertions.assertFalse(jwtUtils.getClaims(token).contains("admin"));
    }

    @Test
    void testSecret() {
        var token = jwtUtils.generateToken(userDetails);

        Assertions.assertTrue(jwtUtils.validateToken(token));
        jwtUtils.setSecret("nowySecret");
        Assertions.assertFalse(jwtUtils.validateToken(token));

    }

    @Test
    void testDuration() throws InterruptedException {
        final long time = 1000L;
        var token1 = jwtUtils.generateToken(userDetails);
        jwtUtils.setJwtTokenValidity(1000);
        var token2 = jwtUtils.generateToken(userDetails);
        Thread.sleep(time * 2);
        Assertions.assertFalse(jwtUtils.isTokenExpired(token1));
        Assertions.assertTrue(jwtUtils.isTokenExpired(token2));
    }

}
