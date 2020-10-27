package com.epam.esm.exception;

public class DaoSaveException extends Exception {
    public DaoSaveException() {
    }

    public DaoSaveException(String message) {
        super(message);
    }

    public DaoSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoSaveException(Throwable cause) {
        super(cause);
    }

    public DaoSaveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
