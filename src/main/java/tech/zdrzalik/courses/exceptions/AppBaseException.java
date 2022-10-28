package tech.zdrzalik.courses.exceptions;

public class AppBaseException extends Exception {
    protected AppBaseException(String message) {
        super(message);
    }

    protected AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}