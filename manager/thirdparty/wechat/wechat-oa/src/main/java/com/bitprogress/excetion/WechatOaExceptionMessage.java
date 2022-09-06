package com.bitprogress.excetion;

import com.bitprogress.exception.IExceptionMessage;

import static com.bitprogress.excetion.WechatOaErrorCodes.*;
import static com.bitprogress.excetion.WechatOaMessageCodes.*;

/**
 * @author wuwuwupx
 */
public enum WechatOaExceptionMessage implements IExceptionMessage {

    /**
     * 消息类型为空
     */
    KF_MESSAGE_TYPE_EMPTY_EXCEPTION(2001, KF_MESSAGE_TYPE_EMPTY_EXCEPTION_CODE, KF_MESSAGE_TYPE_EMPTY_EXCEPTION_MESSAGE),

    /**
     * 消息内容为空
     */
    KF_MESSAGE_EMPTY_EXCEPTION(2002, KF_MESSAGE_EMPTY_EXCEPTION_CODE, KF_MESSAGE_EMPTY_EXCEPTION_MESSAGE),

    /**
     * 消息类型与消息内容不匹配
     */
    KF_MESSAGE_TYPE_MISMATCH_WITH_CONTENT_EXCEPTION(2003, KF_MESSAGE_TYPE_MISMATCH_WITH_CONTENT_EXCEPTION_CODE, KF_MESSAGE_TYPE_MISMATCH_WITH_CONTENT_EXCEPTION_MESSAGE),

    /**
     * 客服账号为空
     */
    KF_SESSION_KF_ACCOUNT_EMPTY_EXCEPTION(2004, KF_SESSION_KF_ACCOUNT_EMPTY_EXCEPTION_CODE, KF_SESSION_KF_ACCOUNT_EMPTY_EXCEPTION_MESSAGE),

    /**
     * 用户openId为空
     */
    KF_SESSION_OPEN_ID_EMPTY_EXCEPTION(2005, KF_SESSION_OPEN_ID_EMPTY_EXCEPTION_CODE, KF_SESSION_OPEN_ID_EMPTY_EXCEPTION_MESSAGE),

    /**
     * 查询时间为空
     */
    MESSAGE_RECORD_TIME_EMPTY_EXCEPTION(2006, MESSAGE_RECORD_TIME_EMPTY_EXCEPTION_CODE, MESSAGE_RECORD_TIME_EMPTY_EXCEPTION_MESSAGE),

    /**
     * 查询时间异常
     */
    MESSAGE_RECORD_TIME_EXCEPTION(2006, MESSAGE_RECORD_TIME_EMPTY_EXCEPTION_CODE, MESSAGE_RECORD_TIME_EMPTY_EXCEPTION_MESSAGE),

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

    WechatOaExceptionMessage(Integer code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
    }

}
