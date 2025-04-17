package com.bitprogress.exception.constant;

/**
 * 请求的返回信息
 */
public class ExceptionConstants {

    private static final Integer NOT_SUPPORTED = 302;

    /**
     * 请求失败状态码
     */
    public static final Integer REQUEST_FAIL_CODE = 400;

    public static final Integer UN_AUTHORIZATION = 401;

    public static final Integer CAPTCHA_CODE_ERROR = 430;

    public static final Integer SMS_CODE_ERROR = 434;

    public static final Integer FORBIDDEN = 403;

    public static final Integer  NOT_FOUND = 404;

    /**
     * 请求失败的默认状态码
     */
    public static final Integer FAIL_CODE = 500;

    private static final Integer  SERVICE_UN_AVAILABLE = 502;

    /**
     * 请求频率限制
     */
    public static final Integer  LIMIT_REQ = 503;

    public static final Integer  TIMEOUT = 504;

    /**
     * 请求失败的默认message
     */
    public static final String FAIL_MESSAGE = "请求失败！";

}
