package com.sustech.gamercenter.util.exception;

public class ContentNotPurchasedException extends Exception {
    public ContentNotPurchasedException() {
        super("Content not purchased");
    }

    public ContentNotPurchasedException(String message) {
        super(message);
    }

    public ContentNotPurchasedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContentNotPurchasedException(Throwable cause) {
        super(cause);
    }
}
