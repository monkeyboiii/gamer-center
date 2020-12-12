package com.sustech.gamercenter.util.exception;

public class UserAccountLockedException extends Exception {

    public UserAccountLockedException() {
        super("User account locked");
    }

    public UserAccountLockedException(String message) {
        super(message);
    }

    public UserAccountLockedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAccountLockedException(Throwable cause) {
        super(cause);
    }
}
