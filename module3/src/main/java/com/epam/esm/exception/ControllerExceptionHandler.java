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
  public ExceptionResponse handleResourceBuildException(ResourceBuildException ex,
      Locale locale) {
    return new ExceptionResponse(messageSource.getMessage("error.ResourceBuildException", null, locale), "40001");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handleResourceBuildException(EntityValidationException ex,
      Locale locale) {
    return new ExceptionResponse(messageSource.getMessage("error.EntityValidationException", null, locale), "40004");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionResponse handleControllerEntityDeleteException(ControllerEntityDeleteException ex,
      Locale locale) {
    return new ExceptionResponse(messageSource.getMessage("error.ControllerEntityDeleteException", null, locale), "40402");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleControllerEntityUpdateException(ControllerEntityUpdateException ex,
      Locale locale) {
    return new ExceptionResponse(messageSource.getMessage("error.ControllerEntityUpdateException", null, locale), "50001");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleControllerSaveEntityException(ControllerSaveEntityException ex,
      Locale locale) {
    return new ExceptionResponse(messageSource.getMessage("error.ControllerSaveEntityException", null, locale), "50002");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleNotWatchedException(Exception ex,
      Locale locale) {

    return new ExceptionResponse(messageSource.getMessage("error.Exception", null, locale), "50003");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleNotWatchedException(RepositoryDeleteException ex,
      Locale locale) {
    return new ExceptionResponse(messageSource.getMessage("error.RepositoryDeleteException", null, locale), "50002");

  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleNotWatchedException(RepositorySaveException ex,
      Locale locale) {
    return new ExceptionResponse(messageSource.getMessage("error.RepositorySaveException", null, locale),"50002");

  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleNotWatchedException(RepositoryUpdateException ex,
      Locale locale) {
    return new ExceptionResponse(messageSource.getMessage("error.RepositoryUpdateException", null, locale), "50002");

  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleNotWatchedException(EntityNotFoundException ex, Locale locale) {
    return new ExceptionResponse(messageSource.getMessage("error.EntityNotFoundException", null, locale),
        "50002");

  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ExceptionResponse handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, Locale locale) {
    return new ExceptionResponse(messageSource.getMessage("error.MethodArgumentTypeMismatchException", null, locale), "40004");
  }


}
