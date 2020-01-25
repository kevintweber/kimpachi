package com.kevintweber.kimpachi.exception;

public class KimpachiException extends RuntimeException {

    public KimpachiException() {
    }

    public KimpachiException(String message) {
        super(message);
    }

    public KimpachiException(String message, Throwable cause) {
        super(message, cause);
    }

    public KimpachiException(Throwable cause) {
        super(cause);
    }

    public KimpachiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
