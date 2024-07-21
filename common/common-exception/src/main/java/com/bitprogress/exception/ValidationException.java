package com.bitprogress.exception;

import lombok.Getter;

/**
 * Valid data exception
 */
@Getter
public class ValidationException extends CommonException {

    private static final Integer CODE = 400;

    private static final String MESSAGE = "参数异常";

    private Object[] args;

    public ValidationException(Integer code) {
        super(code, MESSAGE);
    }

    public ValidationException(Integer code, String message) {
        super(code, message);
    }

    public ValidationException(Integer code, String message, Object[] args) {
        super(code, message);
        this.args = args;
    }

}
