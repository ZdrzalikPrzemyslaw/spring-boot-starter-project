package tech.zdrzalik.courses.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.zdrzalik.courses.common.I18nCodes;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AuthorizationErrorException extends AppBaseException {

    private AuthorizationErrorException(String message) {
        super(message);
    }

    private AuthorizationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AuthorizationErrorException accountNotConfirmed(Throwable cause) {
        return new AuthorizationErrorException(I18nCodes.ACCOUNT_NOT_CONFIRMED, cause);
    }

    public static AuthorizationErrorException accountNotConfirmed() {
        return accountNotConfirmed(null);
    }

    public static AuthorizationErrorException accountDisabled(Throwable cause) {
        return new AuthorizationErrorException(I18nCodes.ACCOUNT_DISABLED, cause);
    }

    public static AuthorizationErrorException accountDisabled() {
        return accountNotConfirmed(null);
    }

    public static AuthorizationErrorException invalidCredentials(Throwable cause) {
        return new AuthorizationErrorException(I18nCodes.INVALID_CREDENTIALS, cause);
    }

    public static AuthorizationErrorException invalidCredentials() {
        return invalidCredentials(null);
    }

}
