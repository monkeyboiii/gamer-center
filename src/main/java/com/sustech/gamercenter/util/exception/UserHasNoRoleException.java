package com.sustech.gamercenter.util.exception;

public class UserHasNoRoleException extends Exception {
    public UserHasNoRoleException() {
        super("User has no such role");
    }

    public UserHasNoRoleException(String message) {
        super(message);
    }

    public UserHasNoRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserHasNoRoleException(Throwable cause) {
        super(cause);
    }
}
