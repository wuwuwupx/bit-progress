package com.bitprogress.exception;

import com.bitprogress.constant.ResultConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 项目通用异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonException extends RuntimeException {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 异常信息
     */
    private String message;

    /**
     * Constructs a exception about message
     */
    public CommonException(String message) {
        this(ResultConstants.FAIL_CODE, message);
    }

    /**
     * Constructs a exception about {@link IException}
     */
    public CommonException(IException exception) {
        this(exception.getCode(), exception.getMessage());
    }

    /**
     * Constructs a exception about Throwable and message
     * default code {@link ResultConstants#FAIL_CODE}
     */
    public CommonException(Throwable cause, String message) {
        this(cause, ResultConstants.FAIL_CODE, message);
    }

    /**
     * Constructs a exception about Throwable and {@link IException}
     */
    public CommonException(Throwable cause, IException exception) {
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

    public static CommonException error(IException exception) {
        return new CommonException(exception);
    }

    public static CommonException error(Throwable cause, IException exception) {
        return new CommonException(cause, exception);
    }

}
