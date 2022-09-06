package com.bitprogress.exception;

import static com.bitprogress.exception.ErrorCodes.*;
import static com.bitprogress.exception.MessageCodes.*;

/**
 * @author wuwuwupx
 * create on 2021/6/26 20:08
 * 请求异常信息枚举
 */
public enum ExceptionMessage implements IExceptionMessage {

    /**
     * 请求参数错误或者参数为空
     */
    REQUEST_ARGUMENT(1001, AUTH_ACCOUNT_PASSWORD_WRONG_CODE, AUTH_ACCOUNT_PASSWORD_WRONG_MESSAGE),
    /**
     * 参数类型没有指定
     */
    TYPE_NOT_APPOINT(1002, TYPE_NOT_APPOINT_EXCEPTION_CODE, TYPE_NOT_APPOINT_MESSAGE),
    /**
     * 非网关转发
     */
    NON_GATEWAY_FORWARD_ERROR(1003, NON_GATEWAY_FORWARD_EXCEPTION_CODE, NON_GATEWAY_FORWARD_MESSAGE),
    /**
     * 非内部访问
     */
    NON_INTERNAL_ACCESS_ERROR(1004, NON_INTERNAL_ACCESS_CODE, NON_INTERNAL_ACCESS_MESSAGE),
    /**
     * 验证码错误
     */
    AUTH_PICCAPTCHA_WRONG(1005, AUTH_PICCAPTCHA_WRONG_CODE, AUTH_PICCAPTCHA_WRONG_MESSAGE),
    /**
     * 验证码已失效
     */
    AUTH_PICCAPTCHA_LOST(1006, AUTH_PICCAPTCHA_LOST_CODE, AUTH_PICCAPTCHA_LOST_MESSAGE),
    /**
     * 解密失败
     */
    RSAUtil_DECRYPT_ERROR(1007, RSAUTIL_DECRYPT_ERROR_CODE, RSAUTIL_DECRYPT_ERROR_MESSAGE),
    /**
     * 账号密码出错
     */
    AUTH_ACCOUNT_PASSWORD_WRONG(1008, AUTH_ACCOUNT_PASSWORD_WRONG_CODE, AUTH_ACCOUNT_PASSWORD_WRONG_MESSAGE),
    /**
     * 账号已被禁用
     */
    ACCOUNT_HAS_DISABLED(1009, ACCOUNT_HAS_DISABLED_CODE, ACCOUNT_HAS_DISABLED_MESSAGE),
    /**
     * 账号已存在
     */
    ACCOUNT_ALREADY_EXIST(1010, ACCOUNT_ALREADY_EXIST_CODE, ACCOUNT_ALREADY_EXIST_MESSAGE),

    /**
     * Url不能为空
     */
    URL_EMPTY_EXCEPTION(1010, URL_EMPTY_EXCEPTION_CODE, URL_EMPTY_EXCEPTION_MESSAGE),

    /**
     * 获取锁失败
     */
    ACQUIRE_LOCK_EXCEPTION(1011, ACQUIRE_LOCK_EXCEPTION_CODE, ACQUIRE_LOCK_EXCEPTION_MESSAGE),

    /**
     * 短信验证码错误
     */
    SMS_CAPTCHA_WRONG_EXCEPTION(1012, SMS_CAPTCHA_WRONG_EXCEPTION_CODE, SMS_CAPTCHA_WRONG_EXCEPTION_MESSAGE),

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

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ExceptionMessage(Integer code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
    }

}
