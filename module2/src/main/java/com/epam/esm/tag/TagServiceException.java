package com.epam.esm.tag;

public class TagServiceException extends Exception {

    public TagServiceException(String message) {
        super(message);
    }

    public TagServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
