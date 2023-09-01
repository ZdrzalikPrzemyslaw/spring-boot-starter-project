package tech.beetwin.template.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.beetwin.template.model.AccessLevel.AccessLevel;
import tech.beetwin.template.model.AccessLevel.AccessLevelsEntity;
import tech.beetwin.template.model.AccountInfo.AccountInfoEntity;

class AccessLevelEntityTest {
    private AccessLevelsEntity entity;
    @BeforeEach
    void beforeAll() {
        entity = new AccessLevelsEntity()
                .setLevel(AccessLevel.USER)
                .setEnabled(true)
                .setId(1234L)
                .setVersion(123456L);
    }

    @Test
    void entityGetLevelTest() {
        Assertions.assertEquals(AccessLevel.USER, entity.getLevel());
        entity.setLevel(AccessLevel.ADMIN);
        Assertions.assertEquals(AccessLevel.ADMIN, entity.getLevel());
    }

    @Test
    void entityGetEnabledTest() {
        Assertions.assertTrue(entity.isEnabled());
        entity.setEnabled(false);
        Assertions.assertFalse(entity.isEnabled());
    }

    @Test
    void entityGetIdTest() {
        Assertions.assertEquals(1234L, entity.getId());
        entity.setId(12345L);
        Assertions.assertEquals(12345L, entity.getId());
    }

    @Test
    void entityGetVersionTest() {
        Assertions.assertEquals(123456L, entity.getVersion());
        entity.setVersion(12345L);
        Assertions.assertEquals(12345L, entity.getVersion());
    }

    @Test
    void entityGetAccountInfoEntityTest() {
        Assertions.assertNull(entity.getAccountInfoId());
        entity.setAccountInfoId(new AccountInfoEntity().setId(123L));
        Assertions.assertNotNull(entity.getAccountInfoId());
        Assertions.assertEquals(123L, entity.getAccountInfoId().getId());
    }
}
