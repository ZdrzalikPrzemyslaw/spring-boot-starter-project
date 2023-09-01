package tech.beetwin.template.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AppBaseException extends RuntimeException {
    protected AppBaseException(String message) {
        super(message);
    }

    protected AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    private AppBaseException() {

    }
}