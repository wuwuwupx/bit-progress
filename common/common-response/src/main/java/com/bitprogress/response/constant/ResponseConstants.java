package com.bitprogress.response.constant;

import com.bitprogress.basemodel.Constant;

/**
 * 请求的返回信息
 */
public class ResponseConstants implements Constant {

    /**
     * 请求成功的默认状态码
     */
    public static final Integer SUCCESS_CODE = 200;

    /**
     * 请求失败的默认状态码
     */
    public static final Integer FAIL_CODE = 500;

    /**
     * 请求成功的默认message
     */
    public static final String SUCCESS_MESSAGE = "请求成功！";

    /**
     * 请求失败的默认error
     */
    public static final String FAIL_ERROR = "FAIL";

    /**
     * 请求失败的默认message
     */
    public static final String FAIL_MESSAGE = "请求失败！";

}
