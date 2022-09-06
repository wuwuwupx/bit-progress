package com.bitprogress.model.managerlog.envm;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author wuwuwupx
 */
public enum MethodEnum implements IEnum<Integer> {

    /**
     * GET请求
     */
    GET(0, "GET"),

    /**
     * HEAD请求
     */
    HEAD(1, "HEAD"),

    /**
     * POST请求
     */
    POST(2, "POST"),

    /**
     * PUT请求
     */
    PUT(3, "PUT"),

    /**
     * PATH请求
     */
    PATCH(4, "PATCH"),

    /**
     * DELETE请求
     */
    DELETE(5, "DELETE"),

    /**
     * OPTIONS请求
     */
    OPTIONS(6, "OPTIONS"),

    /**
     * TRACE请求
     */
    TRACE(7, "TRACE"),

    ;

    private Integer value;
    private String name;

    MethodEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
