package tech.zdrzalik.courses.exceptions;

import tech.zdrzalik.courses.common.I18nCodes;

public class EntityException extends AppBaseException {

    private String className;
    private Long id;
    protected EntityException(String message) {
        super(message);
    }

    protected EntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getClassName() {
        return className;
    }

    public Long getId() {
        return id;
    }

    public EntityException setClassName(String className) {
        this.className = className;
        return this;
    }

    public EntityException setId(Long id) {
        this.id = id;
        return this;
    }

    public static <T> EntityException entityNotFound(Class<T> t, Long id) {
        return new EntityException(I18nCodes.ENTITY_NOT_FOUND).setClassName(t.getName()).setId(id);
    }
}
