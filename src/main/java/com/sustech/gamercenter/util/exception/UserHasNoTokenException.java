package com.sustech.gamercenter.util.exception;

public class UserHasNoTokenException extends Exception{
    public UserHasNoTokenException() {
        super("User has no token");
    }

    public UserHasNoTokenException(String message) {
        super(message);
    }

    public UserHasNoTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserHasNoTokenException(Throwable cause) {
        super(cause);
    }
}
