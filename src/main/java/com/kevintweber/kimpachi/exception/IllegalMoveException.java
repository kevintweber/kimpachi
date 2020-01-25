package com.kevintweber.kimpachi.exception;

public class IllegalMoveException extends KimpachiException {

    public IllegalMoveException() {
    }

    public IllegalMoveException(String message) {
        super(message);
    }

    public IllegalMoveException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalMoveException(Throwable cause) {
        super(cause);
    }

    public IllegalMoveException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
