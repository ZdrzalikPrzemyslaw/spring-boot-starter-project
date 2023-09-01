package tech.beetwin.template.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.beetwin.template.model.AccessLevel.AccessLevelsEntity;

import java.util.Objects;

class VersionJWTUtilsTest {

    private VersionJWTUtils jwtUtils;
    private AccessLevelsEntity abstractEntity;

    @BeforeEach
    void beforeEach() {
        jwtUtils = new VersionJWTUtils().setSecret("secret1").setDuration(180000);
        abstractEntity = new AccessLevelsEntity().setVersion(0L).setId(0);
    }

    @Test
    void testCorrect() {
        var token = jwtUtils.generateToken(abstractEntity);
        Assertions.assertTrue(jwtUtils.validateToken(token));
        Assertions.assertEquals(abstractEntity.getId(), jwtUtils.getId(token));
        Assertions.assertEquals(abstractEntity.getVersion(), jwtUtils.getVersion(token));
        Assertions.assertEquals(Objects.hash(abstractEntity.getClass().getSimpleName()), jwtUtils.getNameHash(token));
    }

    @Test
    void testIncorrect() {
        var token = jwtUtils.generateToken(abstractEntity);
        abstractEntity.setVersion(1L).setId(1);
        Assertions.assertTrue(jwtUtils.validateToken(token));
        Assertions.assertNotEquals(abstractEntity.getId(), jwtUtils.getId(token));
        Assertions.assertNotEquals(abstractEntity.getVersion(), jwtUtils.getVersion(token));
    }

    @Test
    void testSecret() {
        var token = jwtUtils.generateToken(abstractEntity);

        Assertions.assertTrue(jwtUtils.validateToken(token));
        jwtUtils.setSecret("nowySecret");
        Assertions.assertFalse(jwtUtils.validateToken(token));

    }

    @Test
    void testDuration() throws InterruptedException {
        final long time = 1000L;
        var token1 = jwtUtils.generateToken(abstractEntity);
        jwtUtils.setDuration(1000);
        var token2 = jwtUtils.generateToken(abstractEntity);
        Thread.sleep(time * 2);
        Assertions.assertFalse(jwtUtils.isTokenExpired(token1));
        Assertions.assertTrue(jwtUtils.isTokenExpired(token2));
    }
}
