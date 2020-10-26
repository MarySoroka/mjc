package com.epam.esm.tag;

public class TagControllerException extends Exception {
    public TagControllerException() {
    }

    public TagControllerException(String message) {
        super(message);
    }

    public TagControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TagControllerException(Throwable cause) {
        super(cause);
    }

    public TagControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
