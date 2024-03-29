package tech.beetwin.template.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.beetwin.template.common.I18nCodes;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AccountInfoException extends AppBaseException {
    private AccountInfoException(String message) {
        super(message);
    }

    private AccountInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AccountInfoException emailAlreadyExists() {
        return new AccountInfoException(I18nCodes.EMAIL_EXIST);
    }

    public static AccountInfoException accountNotFound() {
        return new AccountInfoException(I18nCodes.ACCOUNT_NOT_FOUND);
    }
}
