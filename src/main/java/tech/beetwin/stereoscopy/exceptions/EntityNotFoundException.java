package tech.beetwin.stereoscopy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.beetwin.stereoscopy.common.I18nCodes;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends AppBaseException {
    private Long id;

    private EntityNotFoundException(String message) {
        super(message);
    }

    private EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public static EntityNotFoundException entityNotFound(Long id) {
        return new EntityNotFoundException(I18nCodes.ENTITY_NOT_FOUND).setId(id);
    }

    public Long getId() {
        return id;
    }

    private EntityNotFoundException setId(Long id) {
        this.id = id;
        return this;
    }
}
