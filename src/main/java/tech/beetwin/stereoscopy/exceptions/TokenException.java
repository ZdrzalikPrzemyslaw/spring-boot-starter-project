package tech.beetwin.stereoscopy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.beetwin.stereoscopy.common.I18nCodes;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TokenException extends AppBaseException {

    protected TokenException(String message) {
        super(message);
    }

    protected TokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public static TokenException invalidToken(Throwable cause) {
        return new TokenException(I18nCodes.INVALID_TOKEN, cause);
    }

    public static TokenException invalidToken() {
        return invalidToken(null);
    }

    public static TokenException notCurrentUserToken(Throwable cause) {
        return new TokenException(I18nCodes.NOT_CURRENT_USER_TOKEN, cause);
    }

    public static TokenException notCurrentUserToken() {
        return notCurrentUserToken(null);
    }

    public static TokenException invalidRefreshToken(Throwable cause) {
        return new TokenException(I18nCodes.INVALID_REFRESH_TOKEN, cause);
    }

    public static TokenException invalidRefreshToken() {
        return invalidRefreshToken(null);
    }

    public static TokenException notCurrentUserRefreshToken(Throwable cause) {
        return new TokenException(I18nCodes.NOT_CURRENT_USER_REFRESH_TOKEN, cause);
    }

    public static TokenException notCurrentUserRefreshToken() {
        return notCurrentUserRefreshToken(null);
    }
}
