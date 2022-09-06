package com.bitprogress.excetion;

import com.bitprogress.exception.IExceptionMessage;
import com.bitprogress.exception.WechatException;

/**
 * @author wuwuwupx
 *  微信模块异常
 */
public class WechatOaException extends WechatException {
    public WechatOaException(IExceptionMessage requestException) {
        super(requestException);
    }
}
