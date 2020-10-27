package com.epam.esm.gift_certificate;

public class GiftCertificateServiceException extends Exception {
    public GiftCertificateServiceException() {
    }

    public GiftCertificateServiceException(String message) {
        super(message);
    }

    public GiftCertificateServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public GiftCertificateServiceException(Throwable cause) {
        super(cause);
    }

    public GiftCertificateServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
