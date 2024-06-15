package com.bitprogress.exception;

import lombok.Getter;

/**
 * Valid data exception
 */
@Getter
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


}
