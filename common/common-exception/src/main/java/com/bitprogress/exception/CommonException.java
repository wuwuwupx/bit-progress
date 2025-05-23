package com.bitprogress.exception;

import com.bitprogress.basemodel.IException;
import com.bitprogress.exception.constant.ExceptionConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目通用异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class CommonException extends RuntimeException {

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 异常信息
     */
    private final String message;

    /**
     * Constructs a exception about code
     */
    public CommonException(Integer code) {
        this(code, ExceptionConstants.FAIL_MESSAGE);
    }

    /**
     * Constructs a exception about message
     */
    public CommonException(String message) {
        this(ExceptionConstants.FAIL_CODE, message);
    }

    /**
     * Constructs a exception about {@link IException}
     */
    public CommonException(ExceptionMessage exception) {
        this(exception.getCode(), exception.getMessage());
    }

    /**
     * Constructs a exception about Throwable and code
     * default code {@link ExceptionConstants#FAIL_CODE}
     */
    public CommonException(Throwable cause, Integer code) {
        this(cause, code, ExceptionConstants.FAIL_MESSAGE);
    }

    /**
     * Constructs a exception about Throwable and message
     * default code {@link ExceptionConstants#FAIL_CODE}
     */
    public CommonException(Throwable cause, String message) {
        this(cause, ExceptionConstants.FAIL_CODE, message);
    }

    /**
     * Constructs a exception about Throwable and {@link IException}
     */
    public CommonException(Throwable cause, ExceptionMessage exception) {
        this(cause, exception.getCode(), exception.getMessage());
    }

    /**
     * Constructs a exception about Throwable and cede and message
     */
    public CommonException(Throwable cause, Integer code, String message) {
        super(cause);
        this.code = code;
        this.message = message;
    }

    public static CommonException error(String message) {
        return new CommonException(message);
    }

    public static CommonException error(Throwable cause, String message) {
        return new CommonException(cause, message);
    }

    public static CommonException error(ExceptionMessage exception) {
        return new CommonException(exception);
    }

    public static CommonException error(Throwable cause, ExceptionMessage exception) {
        return new CommonException(cause, exception);
    }

}
