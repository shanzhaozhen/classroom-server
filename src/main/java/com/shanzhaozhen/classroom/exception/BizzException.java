package com.shanzhaozhen.classroom.exception;

public class BizzException extends RuntimeException {

    private Integer code;

    public BizzException(String message) {
        super(message);
        this.code = code;
    }

    public BizzException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
