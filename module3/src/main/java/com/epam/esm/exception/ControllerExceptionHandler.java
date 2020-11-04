package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ExceptionResponse handleControllerEntityNotFoundException(
      ControllerEntityNotFoundException ex) {
    return new ExceptionResponse(ex.getMessage(), "404");
  }

  @ExceptionHandler
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ExceptionResponse handleControllerEntityDeleteException(
      ControllerEntityDeleteException ex) {
    return new ExceptionResponse(ex.getMessage(), "404");
  }

  @ExceptionHandler
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleControllerEntityUpdateException(
      ControllerEntityUpdateException ex) {
    return new ExceptionResponse(ex.getMessage(), "500");
  }

  @ExceptionHandler
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleControllerSaveEntityException(
      ControllerSaveEntityException ex) {
    return new ExceptionResponse(ex.getMessage(), "500");
  }

  @ExceptionHandler
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleNotWatchedException(
      Exception ex) {
    return new ExceptionResponse(ex.getMessage() == null ? "Something went wrong" : ex.getMessage(),
        "500");
  }

  @ExceptionHandler
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected ExceptionResponse handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex) {
    return new ExceptionResponse(
        ex.getMessage() == null ? "Entity fields type mismatch" :
            ex.getMessage(),
        "400");
  }


}
