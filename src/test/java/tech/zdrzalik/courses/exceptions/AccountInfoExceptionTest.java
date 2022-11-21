package tech.zdrzalik.courses.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.zdrzalik.courses.common.I18nCodes;

@SpringBootTest
class AccountInfoExceptionTest {

    @Test
    void throwAccountInfoExceptionAccountNotFound() {
        Assertions.assertThrows(AccountInfoException.class, (() -> {
            throw AccountInfoException.accountNotFound();
        }), I18nCodes.ACCOUNT_NOT_FOUND);
    }

    @Test
    void throwAccountInfoExceptionEmailAlreadyExists() {
        Assertions.assertThrows(AccountInfoException.class, (() -> {
            throw AccountInfoException.emailAlreadyExists();
        }), I18nCodes.EMAIL_EXIST);
    }
}
