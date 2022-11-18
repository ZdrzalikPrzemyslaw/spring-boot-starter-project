package tech.zdrzalik.courses.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.zdrzalik.courses.DTO.Response.ErrorResponseDTO;
import tech.zdrzalik.courses.common.I18nCodes;
import tech.zdrzalik.courses.exceptions.AppBaseException;
import tech.zdrzalik.courses.exceptions.EntityNotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

@ControllerAdvice
public class ExceptionHandlers {

    private final BasicErrorController basicErrorController;

    public ExceptionHandlers(BasicErrorController basicErrorController) {
        this.basicErrorController = basicErrorController;
    }

    @ExceptionHandler(Exception.class)
    public Object handleAllExceptions(Exception e, HttpServletRequest request, HttpServletResponse response) {
        return handle(e, request, response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public Object handleEntityNotFoundException(EntityNotFoundException e, HttpServletRequest request, HttpServletResponse response) {
        return handle(e, request, response, HttpStatus.NOT_FOUND, I18nCodes.ENTITY_NOT_FOUND);
    }

    // TODO: 18/11/2022 Kiedy robimy tutaj obsluge, to 401 i 403 sa takie same
    //      Obsluga taka jak w tech.zdrzalik.courses.security.SecurityConfiguration dziala ale przy 403 wylewa sie wyjatek
    @ExceptionHandler(AccessDeniedException.class)
    public Object handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request, HttpServletResponse response) {
        throw e;
    }

    /**
     * We exclude all exceptions deriving from {@link AppBaseException} from custom exception handling.
     */
    @ExceptionHandler(AppBaseException.class)
    public Object handleAppBaseException(AppBaseException e, HttpServletRequest request, HttpServletResponse response) {
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

}
