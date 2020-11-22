package com.sustech.gamercenter.util.exception;

public class UnauthorizedAttemptException extends Exception{
    public UnauthorizedAttemptException() {
        super("Unauthorized attempt");
    }

    public UnauthorizedAttemptException(String message) {
        super(message);
    }

    public UnauthorizedAttemptException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedAttemptException(Throwable cause) {
        super(cause);
    }
}
