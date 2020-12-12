package com.sustech.gamercenter.util.exception;

public class UploadFileException extends Exception {
    public UploadFileException() {
        super("Failed to upload");
    }

    public UploadFileException(String message) {
        super(message);
    }

    public UploadFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public UploadFileException(Throwable cause) {
        super(cause);
    }
}
