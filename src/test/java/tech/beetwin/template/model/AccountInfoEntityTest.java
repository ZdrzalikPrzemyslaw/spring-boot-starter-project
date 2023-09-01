package tech.beetwin.template.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.beetwin.template.model.AccountInfo.AccountInfoEntity;

class AccountInfoEntityTest {

        private AccountInfoEntity entity;
        @BeforeEach
        void beforeAll() {
            entity = new AccountInfoEntity()
                    .setEnabled(true)
                    .setId(1234L)
                    .setVersion(123456L)
                    .setConfirmed(true)
                    .setEmail("admin@userowy.com")
                    .setPassword("UnencryptedPass");
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
    void entityGetConfirmedTest() {
        Assertions.assertTrue(entity.isConfirmed());
        entity.setConfirmed(false);
        Assertions.assertFalse(entity.isConfirmed());
    }

    @Test
    void entityGetEnabledTest() {
        Assertions.assertTrue(entity.isEnabled());
        entity.setEnabled(false);
        Assertions.assertFalse(entity.isEnabled());
    }

    @Test
    void entityGetEmailTest() {
        Assertions.assertEquals("admin@userowy.com", entity.getEmail());
        entity.setEmail("admin@adminowy.com");
        Assertions.assertEquals("admin@adminowy.com", entity.getEmail());
    }

    @Test
    void entityGetPasswordTest() {
        Assertions.assertEquals("UnencryptedPass", entity.getPassword());
        entity.setPassword("PassUnencrypted");
        Assertions.assertEquals("PassUnencrypted", entity.getPassword());
    }




}
