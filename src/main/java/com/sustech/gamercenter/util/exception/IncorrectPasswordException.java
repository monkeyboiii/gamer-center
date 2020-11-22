package com.sustech.gamercenter.util.exception;

public class IncorrectPasswordException extends Exception {
    public IncorrectPasswordException() {
        super("Incorrect password");
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }

    public IncorrectPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectPasswordException(Throwable cause) {
        super(cause);
    }
}
