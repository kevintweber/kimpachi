package com.kevintweber.kimpachi.exception;

public class ColorRequiredException extends KimpachiException {

    public ColorRequiredException() {
    }

    public ColorRequiredException(String message) {
        super(message);
    }

    public ColorRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public ColorRequiredException(Throwable cause) {
        super(cause);
    }

    public ColorRequiredException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
