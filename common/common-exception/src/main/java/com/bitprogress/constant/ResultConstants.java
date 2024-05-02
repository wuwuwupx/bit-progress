package com.bitprogress.constant;

/**
 * 请求的返回信息
 */
public class ResultConstants {

    /**
     * 请求成功的默认状态码
     */
    public static final Integer SUCCESS_CODE = 200;

    /**
     * 请求失败状态码
     */
    public static final Integer REQUEST_FAIL_CODE = 400;

    /**
     * 请求失败的默认状态码
     */
    public static final Integer FAIL_CODE = 500;

    private static final Integer NOT_SUPPORTED = 302;

    private static final Integer UN_AUTHORIZATION = 401;

    private static final Integer CAPTCHA_CODE_ERROR = 430;

    private static final Integer SMS_CODE_ERROR = 434;

    private static final Integer FORBIDDEN = 403;

    private static final Integer  NOT_FOUND = 404;

    private static final Integer  ERROR = 500;

    private static final Integer  SERVICE_UN_AVAILABLE = 502;

    /**
     * 请求频率限制
     */
    private static final Integer  LIMIT_REQ = 503;

    private static final Integer  TIMEOUT = 504;

    /**
     * 请求成功的默认message
     */
    public static final String SUCCESS_MESSAGE = "请求成功！";

    /**
     * 请求失败的默认message
     */
    public static final String FAIL_MESSAGE = "请求失败！";

}
