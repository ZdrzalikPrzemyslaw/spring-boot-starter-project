package tech.zdrzalik.courses.utils;

//@ControllerAdvice
//public class ExceptionController extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(AppBaseException.class)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public MessageResponseDTO handleAppBaseException(Exception e) {
//        return new MessageResponseDTO().setMessage(e.getMessage());
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseBody
//    public ResponseEntity<MessageResponseDTO> handleUnauthorizedException(AuthenticationException e)  {
//        return new ResponseEntity<MessageResponseDTO>(new MessageResponseDTO().setMessage("unauthorized"), HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    @ResponseBody
//    public MessageWithClassResponseDTO handleEntityException(EntityNotFoundException e)  {
//        return new MessageWithClassResponseDTO().setMessage(e.getMessage()).setClassName(e.getClassName()).setId(e.getId());
//    }
//    @ExceptionHandler(EntityNotFoundExceptionThymeleaf.class)
//    public ModelAndView handleEntityExceptionThymeleaf(EntityNotFoundException e)  {
//        var modelAndView = new ModelAndView("error");
//        modelAndView.addObject("exception", e);
//        return modelAndView;
//    }
//
//}
