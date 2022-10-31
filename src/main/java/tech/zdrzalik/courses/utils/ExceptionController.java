package tech.zdrzalik.courses.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.zdrzalik.courses.DTO.Response.MessageResponseDTO;
import tech.zdrzalik.courses.exceptions.AppBaseException;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AppBaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResponseDTO HandleException(Exception e){
        return new MessageResponseDTO().setMessage(e.getMessage());
    }
}
