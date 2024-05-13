package com.bitprogress.annotation;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 解析类型
 */
@Getter
@AllArgsConstructor
public enum ParserType {

    /**
     * 忽略解析
     */
    EXCLUDE(0),

    /**
     * 强制解析
     */
    INCLUDE(1),

    ;

    private final int value;

}
