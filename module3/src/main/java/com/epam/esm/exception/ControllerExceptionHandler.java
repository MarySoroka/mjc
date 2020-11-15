package com.epam.esm.exception;

import com.epam.esm.resource.OrderResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionResponse handleControllerEntityNotFoundException(
      ControllerEntityNotFoundException ex) {
    return new ExceptionResponse(ex.getMessage(), "40401");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handleResourceBuildException(
      ResourceBuildException ex) {
    return new ExceptionResponse(ex.getMessage(), "40001");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionResponse handleControllerEntityDeleteException(
      ControllerEntityDeleteException ex) {
    return new ExceptionResponse(ex.getMessage(), "40402");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleControllerEntityUpdateException(
      ControllerEntityUpdateException ex) {
    return new ExceptionResponse(ex.getMessage(), "50001");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleControllerSaveEntityException(
      ControllerSaveEntityException ex) {
    return new ExceptionResponse(ex.getMessage(), "50002");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleNotWatchedException(
      Exception ex) {

    return new ExceptionResponse(ex.getMessage() == null ? "Fatal error" :
        ex.getMessage(), "50003");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleNotWatchedException(
      RepositoryDeleteException ex) {
    return new ExceptionResponse(ex.getMessage(), "50002");

  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleNotWatchedException(
      RepositorySaveException ex) {
    return new ExceptionResponse(ex.getMessage(), "50002");

  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleNotWatchedException(
      RepositoryUpdateException ex) {
    return new ExceptionResponse(ex.getMessage(), "50002");

  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleNotWatchedException(
      EntityNotFoundException ex) {
    return new ExceptionResponse(ex.getMessage(), "50002");

  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ExceptionResponse handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex) {
    return new ExceptionResponse(
        ex.getMessage() == null ? "Entity fields type mismatch" :
            ex.getMessage(),
        "40004");
  }


}
