package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(GiftCertificateNotFoundException.class)
  public ResponseEntity<Object> handleGiftCertificateControllerException(
      GiftCertificateControllerException ex, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),"404");
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }
  @ExceptionHandler(TagNotFoundException.class)
  public ResponseEntity<Object> handleTagNotFoundException(
      TagNotFoundException ex, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),"404");
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DaoSaveException.class)
  public ResponseEntity<Object> handleDaoSaveException(
      DaoSaveException ex, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),"414");
    return new ResponseEntity<>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
  }
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleNotWatchedException(
      Exception ex, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse("Something went wrong","500");
    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }


}
