package tech.zdrzalik.courses.exceptions;

import tech.zdrzalik.courses.common.Codes;

public class AccountInfoException extends AppBaseException{
    protected AccountInfoException(String message) {
        super(message);
    }

    protected AccountInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AccountInfoException emailAlreadyExists(){return new AccountInfoException(Codes.EMAIL_EXIST); }
}
