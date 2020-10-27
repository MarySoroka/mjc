package com.epam.esm.exception;

public class GiftCertificateControllerException extends Exception {
    public GiftCertificateControllerException() {
    }

    public GiftCertificateControllerException(String message) {
        super(message);
    }

    public GiftCertificateControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public GiftCertificateControllerException(Throwable cause) {
        super(cause);
    }

    public GiftCertificateControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
