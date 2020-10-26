package com.epam.esm.tag;

public class TagDeleteException extends Exception {
    public TagDeleteException() {
    }

    public TagDeleteException(String message) {
        super(message);
    }

    public TagDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public TagDeleteException(Throwable cause) {
        super(cause);
    }

    public TagDeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
