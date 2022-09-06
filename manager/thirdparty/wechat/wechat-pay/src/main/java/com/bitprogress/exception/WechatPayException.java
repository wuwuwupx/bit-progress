package com.bitprogress.exception;

/**
 * @author wuwuwupx
 * 微信支付异常，继承自 WechatException
 */
public class WechatPayException extends WechatException {

    public WechatPayException(IExceptionMessage requestException) {
        super(requestException);
    }

}
