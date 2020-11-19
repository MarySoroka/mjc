package com.epam.esm.exception;

public class RepositoryUpdateException extends Exception{

  public RepositoryUpdateException() {
  }

  public RepositoryUpdateException(String message) {
    super(message);
  }

  public RepositoryUpdateException(String message, Throwable cause) {
    super(message, cause);
  }

  public RepositoryUpdateException(Throwable cause) {
    super(cause);
  }

  public RepositoryUpdateException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
