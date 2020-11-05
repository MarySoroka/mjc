package com.epam.esm.exception;

public class GiftCertificateNotFoundException extends Exception{

  public GiftCertificateNotFoundException() {
  }

  public GiftCertificateNotFoundException(String message) {
    super(message);
  }

  public GiftCertificateNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public GiftCertificateNotFoundException(Throwable cause) {
    super(cause);
  }

  public GiftCertificateNotFoundException(String message, Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
