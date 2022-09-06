package com.bitprogress.exception;

/**
 * @author wuwuwupx
 */
public class MessageCodes {

    public static final String REQUEST_ARGUMENT_MESSAGE = "请求参数错误或者参数为空";
    public static final String AUTH_ACCOUNT_PASSWORD_WRONG_MESSAGE = "账号或密码错误";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "server_internal";
    public static final String TYPE_NOT_APPOINT_MESSAGE = "参数类型没有指定";
    public static final String NON_GATEWAY_FORWARD_MESSAGE = "非网关转发";
    public static final String NON_INTERNAL_ACCESS_MESSAGE = "非内部访问";
    //验证码相关
    public static final String AUTH_PICCAPTCHA_WRONG_MESSAGE = "验证码错误";
    public static final String AUTH_PICCAPTCHA_LOST_MESSAGE = "验证码已失效";
    public static final String ACCOUNT_HAS_DISABLED_MESSAGE = "账号已被禁用";
    public static final String ACCOUNT_ALREADY_EXIST_MESSAGE = "账号已经存在";
    //加密解密相关
    public static final String RSAUTIL_DECRYPT_ERROR_MESSAGE = "解密失败";

    public static final String URL_EMPTY_EXCEPTION_MESSAGE = "Url为空";

    public static final String ACQUIRE_LOCK_EXCEPTION_MESSAGE = "获取锁失败";
    public static final String SMS_CAPTCHA_WRONG_EXCEPTION_MESSAGE = "短信验证码错误";

}
