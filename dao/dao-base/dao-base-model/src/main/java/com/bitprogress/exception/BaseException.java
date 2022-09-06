package com.bitprogress.exception;

/**
 * @author wuwuwupx
 * 基础模块异常
 */
public class BaseException extends CommonException {
    public BaseException(IExceptionMessage requestException) {
        super(requestException);
    }
}
