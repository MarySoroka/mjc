package com.epam.esm.exception;

public class RepositoryDeleteException extends Exception{

  public RepositoryDeleteException() {
  }

  public RepositoryDeleteException(String message) {
    super(message);
  }

  public RepositoryDeleteException(String message, Throwable cause) {
    super(message, cause);
  }

  public RepositoryDeleteException(Throwable cause) {
    super(cause);
  }

  public RepositoryDeleteException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
