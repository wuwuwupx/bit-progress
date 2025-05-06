package com.bitprogress.securityspring.exception;

import com.bitprogress.exception.ExceptionMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthException implements ExceptionMessage {

    /**
     * token为空
     */
    AUTH_TOKEN_EMPTY(401, "auth_token_empty"),

    /**
     * token检验错误
     */
    AUTH_TOKEN_WRONG(401, "auth_token_wrong"),

    /**
     * 权限不足
     */
    AUTH_PERMISSION_FAILED(403, "auth_permission_failed"),

    /**
     * 请求参数出错
     */
    REQUEST_ARGUMENT(500, "request_argument"),

    ;

    private final Integer code;

    private final String message;

}
