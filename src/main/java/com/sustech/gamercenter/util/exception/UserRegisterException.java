package com.sustech.gamercenter.util.exception;

public class UserRegisterException extends Exception {
    public UserRegisterException() {
        super("User field already registered");
    }

    public UserRegisterException(String message) {
        super(message);
    }

    public UserRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserRegisterException(Throwable cause) {
        super(cause);
    }
}
