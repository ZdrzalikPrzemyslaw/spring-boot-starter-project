package tech.zdrzalik.courses.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.zdrzalik.courses.common.I18nCodes;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AccountInfoException extends AppBaseException {
    protected AccountInfoException(String message) {
        super(message);
    }

    protected AccountInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AccountInfoException emailAlreadyExists() {
        return new AccountInfoException(I18nCodes.EMAIL_EXIST);
    }

    public static AccountInfoException accountNotFound() {
        return new AccountInfoException(I18nCodes.ACCOUNT_NOT_FOUND);
    }
}
