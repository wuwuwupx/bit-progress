package com.bitprogress.exception;

/**
 * Valid data exception
 */
public class ValidationException extends RuntimeException {

    private final String code;
    private String message;
    private Object[] args;

    public ValidationException(String code) {
        this.code = code;
    }

    public ValidationException(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getMessage() {
        return message;
    }

}
