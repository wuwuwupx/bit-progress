package com.bitprogress.exception;

import static com.bitprogress.exception.WechatErrorCodes.*;
import static com.bitprogress.exception.WechatMessageCodes.*;

/**
 * @author wuwuwupx
 */
public enum WechatExceptionMessage implements IExceptionMessage {

    /**
     * 微信应用信息已存在
     */
    WECHAT_INFO_ALREADY_EXISTS_EXCEPTION(2001, WECHAT_INFO_ALREADY_EXISTS_EXCEPTION_CODE, WECHAT_INFO_ALREADY_EXISTS_EXCEPTION_MESSAGE),

    /**
     * 微信应用名称为空
     */
    WECHAT_INFO_NAME_EMPTY_EXCEPTION(2002, WECHAT_INFO_NAME_EMPTY_EXCEPTION_CODE, WECHAT_INFO_NAME_EMPTY_EXCEPTION_MESSAGE),

    /**
     * 微信应用信息不存在
     */
    WECHAT_INFO_NOT_EXISTS_EXCEPTION(2003, WECHAT_INFO_NOT_EXISTS_EXCEPTION_CODE, WECHAT_INFO_NOT_EXISTS_EXCEPTION_MESSAGE),

    /**
     * openId为空
     */
    OPEN_ID_EMPTY_EXCEPTION(2004, OPEN_ID_EMPTY_EXCEPTION_CODE, OPEN_ID_EMPTY_EXCEPTION_MESSAGE),

    /**
     * 密文为空
     */
    ENCRYPTED_DATA_EMPTY_EXCEPTION(2005, ENCRYPTED_DATA_EMPTY_EXCEPTION_CODE, ENCRYPTED_DATA_EMPTY_EXCEPTION_MESSAGE),

    /**
     * 数据解密失败
     */
    DATA_ENCRYPTED_EXCEPTION(2006, DATA_ENCRYPTED_EXCEPTION_CODE, DATA_ENCRYPTED_EXCEPTION_MESSAGE),

    ;

    /**
     * 状态码
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
    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
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

    WechatExceptionMessage(Integer code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
    }

}
