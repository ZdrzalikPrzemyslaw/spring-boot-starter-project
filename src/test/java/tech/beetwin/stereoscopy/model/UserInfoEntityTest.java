package tech.beetwin.stereoscopy.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.beetwin.stereoscopy.model.AccountInfo.AccountInfoEntity;
import tech.beetwin.stereoscopy.model.UserInfo.UserInfoEntity;

class UserInfoEntityTest {

        private UserInfoEntity entity;
        @BeforeEach
        void beforeAll() {
            entity = new UserInfoEntity()
                    .setId(1234L)
                    .setVersion(123456L)
                    .setFirstName("admin")
                    .setLastName("userowy");
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
    void entityGetFirstNameTest() {
        Assertions.assertEquals("admin", entity.getFirstName());
        entity.setFirstName("userowy");
        Assertions.assertEquals("userowy", entity.getFirstName());
    }

    @Test
    void entityGetLatNameTest() {
        Assertions.assertEquals("userowy", entity.getLastName());
        entity.setLastName("admin");
        Assertions.assertEquals("admin", entity.getLastName());
    }




}
