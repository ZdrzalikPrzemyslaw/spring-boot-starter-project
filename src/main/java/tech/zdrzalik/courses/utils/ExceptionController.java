package tech.zdrzalik.courses.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.zdrzalik.courses.DTO.Response.MessageResponseDTO;
import tech.zdrzalik.courses.DTO.Response.MessageWithClassResponseDTO;
import tech.zdrzalik.courses.exceptions.AppBaseException;
import tech.zdrzalik.courses.exceptions.EntityNotFoundException;
import tech.zdrzalik.courses.exceptions.EntityNotFoundExceptionThymeleaf;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AppBaseException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResponseDTO handleAppBaseException(Exception e) {
        return new MessageResponseDTO().setMessage(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public MessageWithClassResponseDTO handleEntityException(EntityNotFoundException e)  {
        return new MessageWithClassResponseDTO().setMessage(e.getMessage()).setClassName(e.getClassName()).setId(e.getId());
    }
    @ExceptionHandler(EntityNotFoundExceptionThymeleaf.class)
    public ModelAndView handleEntityExceptionThymeleaf(EntityNotFoundException e)  {
        var modelAndView = new ModelAndView("error");
        modelAndView.addObject("exception", e);
        return modelAndView;
    }

}
