package com.bitprogress.exception;

/**
 * @author wuwuwupx
 * create on 2021/6/26 20:13
 * 自定义异常
 */
public class CommonException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误码
     */
    private String error;

    /**
     * 异常信息
     */
    private String message;

    /**
     * 获取异常状态码
     */
    public Integer getCode() {
        return this.code;
    }

    /**
     * 获取错误码
     */
    public String getError() {
        return this.error;
    }

    /**
     * 获取异常信息
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommonException(Integer code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
    }

    public CommonException(IExceptionMessage requestException) {
        this.code = requestException.getCode();
        this.error = requestException.getError();
        this.message = requestException.getMessage();
    }

    public static CommonException error(IExceptionMessage requestException) {
        return new CommonException(requestException);
    }

}
