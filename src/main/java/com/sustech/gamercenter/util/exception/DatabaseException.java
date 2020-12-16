package com.sustech.gamercenter.util.exception;

public class DatabaseException extends Exception{
    public DatabaseException() {
        super("Database exception");
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }
}
