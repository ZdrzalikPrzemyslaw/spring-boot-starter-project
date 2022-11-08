package tech.zdrzalik.courses.utils;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import tech.zdrzalik.courses.DTO.Response.MessageWithClassResponseDTO;
import tech.zdrzalik.courses.exceptions.EntityNotFoundException;
import tech.zdrzalik.courses.exceptions.EntityNotFoundExceptionThymeleaf;

@ControllerAdvice
public class ThymeleafExceptionController {

//    @ExceptionHandler(EntityNotFoundExceptionThymeleaf.class)
//    public ModelAndView handleEntityException(EntityNotFoundException e)  {
//        var modelAndView = new ModelAndView("error");
//        modelAndView.addObject("exception", e);
//        return modelAndView;
//    }
}
