package com.sustech.gamercenter.util.exception;

public class EmailNotSendException extends Exception {
    public EmailNotSendException() {
        super("Email didn't send out");
    }

    public EmailNotSendException(String message) {
        super(message);
    }

    public EmailNotSendException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailNotSendException(Throwable cause) {
        super(cause);
    }
}
