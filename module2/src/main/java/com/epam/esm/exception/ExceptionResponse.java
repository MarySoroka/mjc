package com.epam.esm.exception;

public class ExceptionResponse {

    private String message;
    private String errorCode;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public ExceptionResponse(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;

    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
