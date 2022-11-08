package tech.zdrzalik.courses.exceptions;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class EntityNotFoundExceptionFactory {

    private EntityNotFoundExceptionFactory(){}

    public static <T> RuntimeException getException(Class<T> t, Long id) {
        var req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (req.getRequestURI().startsWith("/admin")) {
            return EntityNotFoundExceptionThymeleaf.entityNotFound(t, id);
        }
        return EntityNotFoundException.entityNotFound(t, id);
    }
}
