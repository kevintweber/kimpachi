package com.kevintweber.kimpachi.exception;

public class UnconnectedException extends KimpachiException {

    public UnconnectedException() {
    }

    public UnconnectedException(String message) {
        super(message);
    }

    public UnconnectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnconnectedException(Throwable cause) {
        super(cause);
    }

    public UnconnectedException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
