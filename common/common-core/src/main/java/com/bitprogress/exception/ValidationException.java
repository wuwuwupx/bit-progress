package com.bitprogress.exception;

/**
 * @author wuwuwupx
 * 正常业务层面验证数据或服务时，抛出的的ValidationException会被转换成Result返回
 */
public class ValidationException extends RuntimeException {

    private String code;
    private String msg;
    private Object[] args;

    public ValidationException(String code) {
        super(code);
        this.code = code;
    }

    public ValidationException(String code, String msg) {
        super(code);
        this.code = code;
        this.msg = msg;
    }


    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getMsg() {
        return msg;
    }

}
