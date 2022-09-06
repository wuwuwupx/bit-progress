package com.bitprogress.auth.base;

public enum AuthException {

    /**
     * token为空
     */
    AUTH_TOKEN_EMPTY("auth_token_empty", "Unauthorized"),

    /**
     * token检验错误
     */
    AUTH_TOKEN_WRONG("auth_token_wrong", "Unauthorized"),

    /**
     * 权限不足
     */
    AUTH_PERMISSION_FAILED("auth_permission_failed", "Unauthorized"),

    /**
     * 请求参数出错
     */
    REQUEST_ARGUMENT("request_argument", "请求参数错误或者参数为空");

    ;

    private String code;

    private String msg;

    AuthException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
