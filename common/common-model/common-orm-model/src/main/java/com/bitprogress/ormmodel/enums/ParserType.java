package com.bitprogress.ormmodel.enums;

import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 解析类型
 */
@Getter
@AllArgsConstructor
public enum ParserType implements ValueEnum {

    /**
     * 忽略解析
     */
    EXCLUDE(0),

    /**
     * 强制解析
     */
    INCLUDE(1),

    ;

    private final Integer value;

}
