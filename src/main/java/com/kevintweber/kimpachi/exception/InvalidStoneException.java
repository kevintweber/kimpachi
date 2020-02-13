package com.kevintweber.kimpachi.exception;

public class InvalidStoneException extends KimpachiException {

    public InvalidStoneException() {
    }

    public InvalidStoneException(String message) {
        super(message);
    }

    public InvalidStoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidStoneException(Throwable cause) {
        super(cause);
    }

    public InvalidStoneException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
