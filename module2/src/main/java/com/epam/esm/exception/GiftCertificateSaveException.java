package com.epam.esm.exception;

public class GiftCertificateSaveException extends Exception {
    public GiftCertificateSaveException() {
    }

    public GiftCertificateSaveException(String message) {
        super(message);
    }

    public GiftCertificateSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public GiftCertificateSaveException(Throwable cause) {
        super(cause);
    }

    public GiftCertificateSaveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
