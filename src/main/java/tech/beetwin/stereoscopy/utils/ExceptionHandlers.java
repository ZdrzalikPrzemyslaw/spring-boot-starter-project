package tech.beetwin.stereoscopy.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tech.beetwin.stereoscopy.exceptions.EntityNotFoundException;
import tech.beetwin.stereoscopy.DTO.Response.ErrorResponseDTO;
import tech.beetwin.stereoscopy.common.I18nCodes;
import tech.beetwin.stereoscopy.exceptions.AppBaseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

@ControllerAdvice
public class ExceptionHandlers {
    private static Logger logger = LoggerFactory.getLogger(ExceptionHandlers.class);

    private final BasicErrorController basicErrorController;

    public ExceptionHandlers(BasicErrorController basicErrorController) {
        this.basicErrorController = basicErrorController;
    }

    @ExceptionHandler(Exception.class)
    public Object handleAllExceptions(Exception e, HttpServletRequest request, HttpServletResponse response) {
        logException(e, request, response);
        return handle(e, request, response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public Object handleEntityNotFoundException(EntityNotFoundException e, HttpServletRequest request, HttpServletResponse response) {
        logException(e, request, response);
        return handle(e, request, response, HttpStatus.NOT_FOUND, I18nCodes.ENTITY_NOT_FOUND);
    }

    // TODO: 18/11/2022 Kiedy robimy tutaj obsluge, to 401 i 403 sa takie same
    //      Obsluga taka jak w tech.zdrzalik.courses.security.SecurityConfiguration dziala ale przy 403 wylewa sie wyjatek
    @ExceptionHandler(AccessDeniedException.class)
    public Object handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request, HttpServletResponse response) {
        logException(e, request, response);
        throw e;
    }

    /**
     * We exclude all exceptions deriving from {@link AppBaseException} from custom exception handling.
     */
    @ExceptionHandler(AppBaseException.class)
    public Object handleAppBaseException(AppBaseException e, HttpServletRequest request, HttpServletResponse response) {
        logException(e, request, response);
        throw e;
    }

    private Object handle(Exception e, HttpServletRequest request, HttpServletResponse response, HttpStatus status) {
        return handle(e, request, response, HttpStatus.INTERNAL_SERVER_ERROR, I18nCodes.getCodeByStatus(status));
    }

    private Object handle(Exception e, HttpServletRequest request, HttpServletResponse response, HttpStatus status, String message) {
        String header = request.getHeader("Accept");
        if (header != null && header.contains("text/html")) {
            setErrorCode(request, response, status);
            return basicErrorController.errorHtml(request, response);
        }
        return createJsonResponse(message, status, request.getRequestURI());
    }

    private ResponseEntity<ErrorResponseDTO> createJsonResponse(String message, HttpStatus status, String path) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO()
                .setTimestamp(new Timestamp(System.currentTimeMillis()))
                .setStatus(status.value())
                .setMessage(message)
                .setPath(path)
                .setError(status.name().toLowerCase());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.status(status).headers(httpHeaders).body(errorResponseDTO);
    }


    private void setErrorCode(HttpServletRequest request, HttpServletResponse response, HttpStatus httpStatus) {
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, httpStatus.value());
        response.setStatus(httpStatus.value());
    }

    private static void logException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        if (e instanceof AppBaseException) {
            logger.trace("%s thrown with message %s".formatted(e.getClass().getName(), e.getMessage()));
        } else if (e instanceof AccessDeniedException) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            logger.debug("%s thrown with message %s. Unauthorized access attempted. Request uri: %s. Authentication Principal: %s"
                    .formatted(
                            e.getClass().getName(),
                            e.getMessage(),
                            request.getRequestURI(),
                            authentication.getPrincipal()));
        }
        else if (e != null) {
            logger.warn("%s thrown with message %s".formatted(e.getClass().getName(), e.getMessage()));
        }
    }

}
