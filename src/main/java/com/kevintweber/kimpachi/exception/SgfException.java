package com.kevintweber.kimpachi.exception;

public class SgfException extends KimpachiException {

    public SgfException() {
    }

    public SgfException(String message) {
        super(message);
    }

    public SgfException(String message, Throwable cause) {
        super(message, cause);
    }

    public SgfException(Throwable cause) {
        super(cause);
    }

    public SgfException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
