package tech.zdrzalik.courses.exceptions;

import tech.zdrzalik.courses.common.I18nCodes;

public class EntityNotFoundExceptionThymeleaf extends  EntityNotFoundException{
    protected EntityNotFoundExceptionThymeleaf(String message) {
        super(message);
    }

    public EntityNotFoundExceptionThymeleaf(EntityNotFoundException e) {
        super();
        super.setId(e.getId());
        super.setClassName(e.getClassName());
    }

    protected EntityNotFoundExceptionThymeleaf(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundExceptionThymeleaf() {
        super();
    }

    @Override
    public EntityNotFoundExceptionThymeleaf setClassName(String className) {
        super.setClassName(className);
        return this;
    }

    @Override
    public EntityNotFoundExceptionThymeleaf setId(Long id) {
        super.setId(id);
        return this;
    }

    public static <T> EntityNotFoundExceptionThymeleaf entityNotFound(Class<T> cls, Long id) {
        return new EntityNotFoundExceptionThymeleaf().setClassName(cls.getName()).setId(id);
    }
}
