package com.sustech.gamercenter.util.exception;

import lombok.Data;

@Data
public class MyException extends RuntimeException {

    private int code;
    private String message;


    public MyException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
