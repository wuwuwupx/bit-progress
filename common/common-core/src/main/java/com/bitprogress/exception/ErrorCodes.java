package com.bitprogress.exception;

/**
 * @author wuwuwupx
 * 错误码
 */
public class ErrorCodes {

    public static final String REQUEST_ARGUMENT_EXCEPTION_CODE = "REQUEST_ARGUMENT_EXCEPTION";
    public static final String AUTH_ACCOUNT_PASSWORD_WRONG_CODE = "AUTH_ACCOUNT_PASSWORD_WRONG";
    public static final String INTERNAL_SERVER_ERROR_CODE = "INTERNAL_SERVER_ERROR";
    public static final String TYPE_NOT_APPOINT_EXCEPTION_CODE = "TYPE_NOT_APPOINT";
    public static final String NON_GATEWAY_FORWARD_EXCEPTION_CODE = "NON_GATEWAY_FORWARD";
    public static final String NON_INTERNAL_ACCESS_CODE = "NON_INTERNAL_ACCESS";

    public static final String ACCOUNT_HAS_DISABLED_CODE = "ACCOUNT_HAS_DISABLED";
    public static final String ACCOUNT_ALREADY_EXIST_CODE = "ACCOUNT_ALREADY_EXIST";

    //验证码相关

    public static final String AUTH_PICCAPTCHA_WRONG_CODE = "AUTH_PICCAPTCHA_WRONG";
    public static final String AUTH_PICCAPTCHA_LOST_CODE = "AUTH_PICCAPTCHA_LOST";

    /* 加密解密相关 */

    public static final String RSAUTIL_DECRYPT_ERROR_CODE = "RSAUTIL_DECRYPT_ERROR";

    public static final String URL_EMPTY_EXCEPTION_CODE = "URL_EMPTY_EXCEPTION";

    public static final String ACQUIRE_LOCK_EXCEPTION_CODE = "ACQUIRE_LOCK_EXCEPTION";
    public static final String SMS_CAPTCHA_WRONG_EXCEPTION_CODE = "SMS_CAPTCHA_WRONG_EXCEPTION";

}
