package com.bitprogress.exception;

import static com.bitprogress.exception.WechatPayErrorCode.*;
import static com.bitprogress.exception.WechatPayMessageCode.*;

/**
 * @author wuwuwupx
 * 微信支付异常信息 继承自{@link ExceptionMessage}
 */
public enum WechatPayExceptionMessage implements IExceptionMessage {

    /**
     * JSAPI支付统一下单失败
     */
    JSAPI_UNIFIED_ORDER_ERROR(4001, JSAPI_UNIFIED_ORDER_CODE, JSAPI_UNIFIED_ORDER_MESSAGE),
    /**
     * JSAPI支付信息签名失败
     */
    JSAPI_PAY_SIGN_ERROR(4002, JSAPI_PAY_SIGN_CODE, JSAPI_PAY_SIGN_MESSAGE),
    /**
     * APP支付统一下单失败
     */
    APP_PAY_UNIFIED_ORDER_ERROR(4003, APP_PAY_UNIFIED_ORDER_CODE, APP_PAY_UNIFIED_ORDER_MESSAGE),
    /**
     * H5支付统一下单失败
     */
    H5_PAY_UNIFIED_ORDER_ERROR(4004, H5_PAY_UNIFIED_ORDER_CODE, H5_PAY_UNIFIED_ORDER_MESSAGE),
    /**
     * APP支付信息签名失败
     */
    APP_PAY_SIGN_ERROR(4005, JSAPI_PAY_SIGN_CODE, JSAPI_PAY_SIGN_MESSAGE),
    /**
     * 微信支付订单查询失败
     */
    TRANSACTION_QUERY_ERROR(4006, TRANSACTION_QUERY_CODE, TRANSACTION_QUERY_MESSAGE),

    ;

    private Integer code;

    private String error;

    private String message;

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getError() {
        return this.error;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    WechatPayExceptionMessage(Integer code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
    }

}
