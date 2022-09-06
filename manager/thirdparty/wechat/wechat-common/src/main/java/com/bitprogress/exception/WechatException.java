package com.bitprogress.exception;

/**
 * @author wuwuwupx
 *  微信模块异常
 */
public class WechatException extends CommonException {
    public WechatException(IExceptionMessage requestException) {
        super(requestException);
    }
}
