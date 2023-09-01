package tech.beetwin.template.exceptions;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.beetwin.template.common.I18nCodes;

@SpringBootTest
class TokenExceptionTest {

    @Test
    void throwInvalidTokenException() {
        Assertions.assertThrows(TokenException.class, (() -> {
            throw TokenException.invalidToken();
        }), I18nCodes.INVALID_TOKEN);
    }

    @Test
    void throwNotCurrentUserTokenException() {
        Assertions.assertThrows(TokenException.class, (() -> {
            throw TokenException.invalidRefreshToken();
        }), I18nCodes.NOT_CURRENT_USER_TOKEN);
    }

    @Test
    void throwInvalidRefreshTokenException() {
        Assertions.assertThrows(TokenException.class, (() -> {
            throw TokenException.invalidRefreshToken();
        }), I18nCodes.INVALID_REFRESH_TOKEN);
    }

    @Test
    void throwNotCurrentUserRefreshTokenException() {
        Assertions.assertThrows(TokenException.class, (() -> {
            throw TokenException.invalidToken();
        }), I18nCodes.NOT_CURRENT_USER_REFRESH_TOKEN);
    }

}
