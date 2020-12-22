package com.sustech.gamercenter.util.exception;

public class GameOwnershipException extends Exception {

    public GameOwnershipException() {
        super("Invalid game ownership");
    }

    public GameOwnershipException(String message) {
        super(message);
    }

    public GameOwnershipException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameOwnershipException(Throwable cause) {
        super(cause);
    }
}
