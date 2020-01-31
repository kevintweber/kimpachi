package com.kevintweber.kimpachi.exception;

public class NonAdjacentPositionException extends KimpachiException {

    public NonAdjacentPositionException() {
    }

    public NonAdjacentPositionException(String message) {
        super(message);
    }

    public NonAdjacentPositionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonAdjacentPositionException(Throwable cause) {
        super(cause);
    }

    public NonAdjacentPositionException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
