package com.bitprogress.exception;

import lombok.Getter;

/**
 * Valid data exception
 */
@Getter
public class ValidationException extends CommonException {

    private Object[] args;

    public ValidationException(Integer code) {
        super(code);
    }

    public ValidationException(Integer code, String message) {
        super(code, message);
    }

    public ValidationException(Integer code, String message, Object[] args) {
        super(code, message);
        this.args = args;
    }


}
