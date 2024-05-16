package com.bitprogress.mybatisplus.annotation;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * sql解析模式的传播方式
 */
@Getter
@AllArgsConstructor
public enum Propagation {

    /**
     * 跟随当前的解析模式，没有则创建新的解析模式
     */
    REQUIRED(0),

    /**
     * 跟随当前的解析模式，没有则跳过
     */
    SUPPORTS(1),

    /**
     * 开启一个新的解析模式
     */
    REQUIRES_NEW(2),

    /**
     * 关闭解析模式
     */
    NOT_SUPPORTED(3),

    ;

    private final int value;

}
