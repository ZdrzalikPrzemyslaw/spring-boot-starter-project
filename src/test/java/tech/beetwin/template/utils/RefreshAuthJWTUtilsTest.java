package tech.beetwin.template.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import tech.beetwin.template.security.UserDetailsImpl;

import java.util.Collections;

class RefreshAuthJWTUtilsTest {

    private RefreshAuthJWTUtils refreshAuthJWTUtils;
    UserDetailsImpl userDetails;
    private String subject = "test@user.com";

    @BeforeEach
    void beforeEach() {
        refreshAuthJWTUtils = new RefreshAuthJWTUtils().setSecret("secret1").setJwtTokenValidity(180000);
        userDetails = new UserDetailsImpl(Collections.singleton(new SimpleGrantedAuthority("user")),subject,"Password123","Jan","Nowak",true,true,0L);
    }

    @Test
    void testCorrect() {
        var token = refreshAuthJWTUtils.generateToken(userDetails);
        Assertions.assertTrue(refreshAuthJWTUtils.validateToken(token));
        Assertions.assertEquals(subject, refreshAuthJWTUtils.getSubjectFromToken(token));

    }

    @Test
    void testIncorrect() {
        var token = refreshAuthJWTUtils.generateToken(userDetails);
        Assertions.assertTrue(refreshAuthJWTUtils.validateToken(token));
        Assertions.assertNotEquals("another@user.com", refreshAuthJWTUtils.getSubjectFromToken(token));
    }

    @Test
    void testSecret() {
        var token = refreshAuthJWTUtils.generateToken(userDetails);

        Assertions.assertTrue(refreshAuthJWTUtils.validateToken(token));
        refreshAuthJWTUtils.setSecret("nowySecret");
        Assertions.assertFalse(refreshAuthJWTUtils.validateToken(token));

    }

    @Test
    void testDuration() throws InterruptedException {
        final long time = 1000L;
        var token1 = refreshAuthJWTUtils.generateToken(userDetails);
        refreshAuthJWTUtils.setJwtTokenValidity(1000);
        var token2 = refreshAuthJWTUtils.generateToken(userDetails);
        Thread.sleep(time * 2);
        Assertions.assertFalse(refreshAuthJWTUtils.isTokenExpired(token1));
        Assertions.assertTrue(refreshAuthJWTUtils.isTokenExpired(token2));
    }

}
