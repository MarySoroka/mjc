package com.epam.esm.exception;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  MessageSource messageSource;

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionResponse handleControllerEntityNotFoundException(ControllerEntityNotFoundException ex,
      Locale locale) {

    return new ExceptionResponse(messageSource.getMessage("error.ControllerEntityNotFoundException", null, locale),
        "40401");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handleResourceBuildException(ResourceBuildException ex) {
    return new ExceptionResponse(ex.getMessage(), "40001");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handleResourceBuildException(EntityValidationException ex) {
    return new ExceptionResponse(ex.getMessage(), "40004");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionResponse handleControllerEntityDeleteException(ControllerEntityDeleteException ex) {
    return new ExceptionResponse(ex.getMessage(), "40402");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleControllerEntityUpdateException(ControllerEntityUpdateException ex) {
    return new ExceptionResponse(ex.getMessage(), "50001");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleControllerSaveEntityException(ControllerSaveEntityException ex) {
    return new ExceptionResponse(ex.getMessage(), "50002");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleNotWatchedException(Exception ex) {

    return new ExceptionResponse(ex.getMessage() == null ? "Fatal error" : ex.getMessage(), "50003");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleNotWatchedException(RepositoryDeleteException ex) {
    return new ExceptionResponse(ex.getMessage(), "50002");

  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleNotWatchedException(RepositorySaveException ex) {
    return new ExceptionResponse(ex.getMessage(), "50002");

  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleNotWatchedException(RepositoryUpdateException ex) {
    return new ExceptionResponse(ex.getMessage(), "50002");

  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleNotWatchedException(EntityNotFoundException ex, Locale locale) {
    return new ExceptionResponse(messageSource.getMessage("error.ControllerEntityNotFoundException", null, locale),
        "50002");

  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ExceptionResponse handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
    return new ExceptionResponse(ex.getMessage() == null ? "Entity fields type mismatch" : ex.getMessage(), "40004");
  }


}
