package com.bitprogress.annotation;

/**
 * sql解析模式的传播方式
 */
public enum Propagation {

    /**
     * 跟随当前的解析模式，没有则创建新的解析模式
     */
    REQUIRED(1),

    /**
     * 跟随当前的解析模式，没有则跳过
     */
    SUPPORTS(2),

    /**
     * 开启一个新的解析模式
     */
    REQUIRES_NEW(3),

    /**
     * 关闭解析模式
     */
    NOT_SUPPORTED(4),

    ;

    private final Integer value;

    public Integer getValue() {
        return value;
    }

    Propagation(Integer value) {
        this.value = value;
    }

}
