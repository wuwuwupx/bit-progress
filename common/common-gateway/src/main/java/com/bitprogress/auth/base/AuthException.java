package com.bitprogress.auth.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
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

    private final String code;

    private final String msg;

}
