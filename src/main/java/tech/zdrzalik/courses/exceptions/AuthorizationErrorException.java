package tech.zdrzalik.courses.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AuthorizationErrorException extends AppBaseException {

    public AuthorizationErrorException(String message) {
        super(message);
    }

    public AuthorizationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

}
