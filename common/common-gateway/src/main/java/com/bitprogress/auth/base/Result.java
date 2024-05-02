package com.bitprogress.auth.base;

import java.util.Objects;

public class Result {

    private String code;
    private String message;

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Result error(String code, String msg) {
        return new Result(code, msg);
    }

    public static Result error(AuthException authException) {
        if (Objects.isNull(authException)) {
            return error("Internal Server Error", "服务器内部错误");
        }
        return error(authException.getCode(), authException.getMsg());
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
