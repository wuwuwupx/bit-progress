package com.bitprogress.auth.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class Result {

    private final String code;
    private final String message;

    public static Result error(String code, String msg) {
        return new Result(code, msg);
    }

    public static Result error(AuthException authException) {
        if (Objects.isNull(authException)) {
            return error("Internal Server Error", "服务器内部错误");
        }
        return error(authException.getCode(), authException.getMsg());
    }

}
