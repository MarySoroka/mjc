package com.epam.esm.exception;

public class RepositorySaveException extends Exception {
    public RepositorySaveException() {
    }

    public RepositorySaveException(String message) {
        super(message);
    }

    public RepositorySaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositorySaveException(Throwable cause) {
        super(cause);
    }

    public RepositorySaveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
