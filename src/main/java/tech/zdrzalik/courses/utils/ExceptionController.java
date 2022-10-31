package tech.zdrzalik.courses.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.zdrzalik.courses.DTO.Response.MessageResponseDTO;
import tech.zdrzalik.courses.DTO.Response.MessageWithClassResponseDTO;
import tech.zdrzalik.courses.exceptions.AppBaseException;
import tech.zdrzalik.courses.exceptions.EntityNotFoundException;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AppBaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResponseDTO handleAppBaseException(Exception e) {
        return new MessageResponseDTO().setMessage(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageWithClassResponseDTO handleEntityException(EntityNotFoundException e) {
        return new MessageWithClassResponseDTO().setMessage(e.getMessage()).setClassName(e.getClassName()).setId(e.getId());
    }
}
