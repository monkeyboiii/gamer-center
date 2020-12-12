package com.sustech.gamercenter.util.exception;

public class DuplicateCommentException extends Exception {
    public DuplicateCommentException() {
        super("Duplicate comment is not allowed");
    }

    public DuplicateCommentException(String message) {
        super(message);
    }

    public DuplicateCommentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateCommentException(Throwable cause) {
        super(cause);
    }
}
