package tech.zdrzalik.courses.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.zdrzalik.courses.common.I18nCodes;

@SpringBootTest
class AuthorizationErrorExceptionTest {

    @Test
    void throwAuthorizationErrorExceptionAccountDisabled() {
        Assertions.assertThrows(AuthorizationErrorException.class, (() -> {
            throw AuthorizationErrorException.accountDisabled();
        }), I18nCodes.ACCOUNT_DISABLED);
    }

    @Test
    void throwAuthorizationErrorExceptionInvalidCredentials() {
        Assertions.assertThrows(AuthorizationErrorException.class, (() -> {
            throw AuthorizationErrorException.invalidCredentials();
        }), I18nCodes.INVALID_CREDENTIALS);
    }
}
