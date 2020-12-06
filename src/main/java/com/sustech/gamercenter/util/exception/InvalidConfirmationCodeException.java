package com.sustech.gamercenter.util.exception;

public class InvalidConfirmationCodeException extends Exception {
    public InvalidConfirmationCodeException() {
        super("Invalid confirmation code");
    }

    public InvalidConfirmationCodeException(String message) {
        super(message);
    }

    public InvalidConfirmationCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidConfirmationCodeException(Throwable cause) {
        super(cause);
    }
}
