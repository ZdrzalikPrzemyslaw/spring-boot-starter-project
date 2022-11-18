package tech.zdrzalik.courses.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.zdrzalik.courses.common.I18nCodes;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends AppBaseException {

    private Long id;
    protected EntityNotFoundException(String message) {
        super(message);
    }

    protected EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


    public Long getId() {
        return id;
    }

    public EntityNotFoundException setId(Long id) {
        this.id = id;
        return this;
    }

    public static EntityNotFoundException entityNotFound(Long id) {
        return new EntityNotFoundException(I18nCodes.ENTITY_NOT_FOUND).setId(id);
    }
}
