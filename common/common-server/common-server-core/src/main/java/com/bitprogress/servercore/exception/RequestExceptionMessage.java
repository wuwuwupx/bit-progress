package com.bitprogress.servercore.exception;

import com.bitprogress.exception.ExceptionMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RequestExceptionMessage implements ExceptionMessage {

    REQUEST_SOURCE_MISS_WRONG_EXCEPTION(400, "请求来源不能为空"),

    REQUEST_TYPE_MISS_WRONG_EXCEPTION(400, "请求类型不能为空"),

    REQUEST_SOURCE_NOT_APPOINT_EXCEPTION(400, "请求来源不匹配"),

    REQUEST_TYPE_NOT_APPOINT_EXCEPTION(400, "请求类型不匹配"),

    REQUEST_SOURCE_NOT_SUPPORT_EXCEPTION(400, "请求来源不支持"),

    REQUEST_TOKEN_NOT_APPOINT_EXCEPTION(400, "请求权限不匹配"),

    ;

    private final Integer code;

    private final String message;

}
