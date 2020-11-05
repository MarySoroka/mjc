package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<Object> handleControllerEntityNotFoundException(
      ControllerEntityNotFoundException ex) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), "404");
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleControllerEntityDeleteException(
      ControllerEntityDeleteException ex) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), "404");
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleControllerEntityUpdateException(
      ControllerEntityUpdateException ex) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), "500");
    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleControllerSaveEntityException(
      ControllerSaveEntityException ex) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), "500");
    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleNotWatchedException(
      Exception ex) {
    ExceptionResponse exceptionResponse = new ExceptionResponse("Something went wrong", "500");
    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler
  protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(
        ex.getMessage() == null ? "Entity fields type mismatch" :
            ex.getMessage(),
        "400");
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }


}
