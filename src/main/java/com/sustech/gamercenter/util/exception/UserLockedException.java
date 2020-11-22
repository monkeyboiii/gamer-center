package com.sustech.gamercenter.util.exception;

public class UserLockedException extends Exception {
    public UserLockedException() {
        super("User account locked");
    }

    public UserLockedException(String message) {
        super(message);
    }

    public UserLockedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserLockedException(Throwable cause) {
        super(cause);
    }
}
