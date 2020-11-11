package com.epam.esm.exception;

public class ResourceBuildException extends RuntimeException {

  public ResourceBuildException() {
  }

  public ResourceBuildException(String message) {
    super(message);
  }

  public ResourceBuildException(String message, Throwable cause) {
    super(message, cause);
  }

  public ResourceBuildException(Throwable cause) {
    super(cause);
  }

  public ResourceBuildException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
