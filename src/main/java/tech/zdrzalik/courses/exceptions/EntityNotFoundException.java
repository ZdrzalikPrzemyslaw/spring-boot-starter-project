package tech.zdrzalik.courses.exceptions;

import tech.zdrzalik.courses.common.I18nCodes;

public class EntityNotFoundException extends AppBaseException {

    private String className;
    private Long id;
    protected EntityNotFoundException(String message) {
        super(message);
    }

    protected EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getClassName() {
        return className;
    }

    public Long getId() {
        return id;
    }

    public EntityNotFoundException setClassName(String className) {
        this.className = className;
        return this;
    }

    public EntityNotFoundException setId(Long id) {
        this.id = id;
        return this;
    }

    public static <T> EntityNotFoundException entityNotFound(Class<T> t, Long id) {
        return new EntityNotFoundException(I18nCodes.ENTITY_NOT_FOUND).setClassName(t.getName()).setId(id);
    }
}
