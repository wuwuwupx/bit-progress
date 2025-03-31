package com.bitprogress.response.exception;

import com.bitprogress.exception.ExceptionMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.bitprogress.response.exception.CommonExceptionMessage.*;

/**
 * 请求异常信息枚举
 */
@Getter
@AllArgsConstructor
public enum CommonExceptionEnum implements ExceptionMessage {

    /**
     * 请求参数错误或者参数为空
     */
    REQUEST_ARGUMENT(400, AUTH_ACCOUNT_PASSWORD_WRONG_MESSAGE),
    /**
     * 参数类型没有指定
     */
    TYPE_NOT_APPOINT(400, TYPE_NOT_APPOINT_MESSAGE),
    /**
     * 非网关转发
     */
    NON_GATEWAY_FORWARD_ERROR(412, NON_GATEWAY_FORWARD_MESSAGE),
    /**
     * 非内部访问
     */
    NON_INTERNAL_ACCESS_ERROR(412, NON_INTERNAL_ACCESS_MESSAGE),
    /**
     * 验证码错误
     */
    AUTH_PICCAPTCHA_WRONG(400, AUTH_PICCAPTCHA_WRONG_MESSAGE),
    /**
     * 验证码已失效
     */
    AUTH_PICCAPTCHA_LOST(400, AUTH_PICCAPTCHA_LOST_MESSAGE),
    /**
     * 解密失败
     */
    RSAUtil_DECRYPT_ERROR(400, RSAUTIL_DECRYPT_ERROR_MESSAGE),
    /**
     * 账号密码出错
     */
    AUTH_ACCOUNT_PASSWORD_WRONG(400, AUTH_ACCOUNT_PASSWORD_WRONG_MESSAGE),
    /**
     * 账号已被禁用
     */
    ACCOUNT_HAS_DISABLED(400, ACCOUNT_HAS_DISABLED_MESSAGE),
    /**
     * 账号已存在
     */
    ACCOUNT_ALREADY_EXIST(400, ACCOUNT_ALREADY_EXIST_MESSAGE),

    /**
     * Url不能为空
     */
    URL_EMPTY_EXCEPTION(400, URL_EMPTY_EXCEPTION_MESSAGE),

    /**
     * 获取锁失败
     */
    ACQUIRE_LOCK_EXCEPTION(400, ACQUIRE_LOCK_EXCEPTION_MESSAGE),

    /**
     * 短信验证码错误
     */
    SMS_CAPTCHA_WRONG_EXCEPTION(400, SMS_CAPTCHA_WRONG_EXCEPTION_MESSAGE),

    /**
     * 登录信息丢失
     */
    LOGIN_MISS_WRONG_EXCEPTION(400, LOGIN_MISS_WRONG_EXCEPTION_MESSAGE),

    /**
     * 用户ID丢失
     */
    USER_ID_MISS_WRONG_EXCEPTION(400, USER_ID_MISS_WRONG_EXCEPTION_MESSAGE),

    /**
     * 请求失败
     */
    REQUEST_WRONG_EXCEPTION(400, REQUEST_WRONG_EXCEPTION_MESSAGE),

    /**
     * 响应丢失
     */
    RESPONSE_MISS_WRONG_EXCEPTION(400, RESPONSE_MISS_WRONG_EXCEPTION_MESSAGE),

    /**
     * 响应状态码丢失
     */
    RESPONSE_CODE_MISS_WRONG_EXCEPTION(400, RESPONSE_CODE_MISS_WRONG_EXCEPTION_MESSAGE),

    /**
     * 用户未登录
     */
    USER_NOT_LOGIN_EXCEPTION(401, USER_NOT_LOGIN_EXCEPTION_MESSAGE),

    /**
     * 用户未登录
     */
    AUTH_MSG_EXCEPTION(401, AUTH_MSG_EXCEPTION_MESSAGE),

    ;

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 异常信息
     */
    private final String message;

}
