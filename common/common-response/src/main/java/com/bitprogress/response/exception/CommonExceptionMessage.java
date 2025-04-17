package com.bitprogress.response.exception;

/**
 * @author wuwuwupx
 */
public class CommonExceptionMessage {

    public static final String REQUEST_ARGUMENT_MESSAGE = "请求参数错误或者参数为空";
    public static final String AUTH_ACCOUNT_PASSWORD_WRONG_MESSAGE = "账号或密码错误";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "server_internal";
    public static final String FORBIDDEN_EXCEPTION_MESSAGE = "拒绝访问";
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
    public static final String LOGIN_MISS_WRONG_EXCEPTION_MESSAGE = "登录信息丢失";
    public static final String USER_ID_MISS_WRONG_EXCEPTION_MESSAGE = "用户ID丢失";
    public static final String REQUEST_WRONG_EXCEPTION_MESSAGE = "请求失败";
    public static final String RESPONSE_MISS_WRONG_EXCEPTION_MESSAGE = "请求响应丢失";
    public static final String RESPONSE_CODE_MISS_WRONG_EXCEPTION_MESSAGE = "请求响应状态码丢失";
    public static final String USER_NOT_LOGIN_EXCEPTION_MESSAGE = "用户未登录";
    public static final String AUTH_MSG_EXCEPTION_MESSAGE = "用户未登录";

}
