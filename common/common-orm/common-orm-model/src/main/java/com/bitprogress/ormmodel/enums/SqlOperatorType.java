package com.bitprogress.ormmodel.enums;

import com.bitprogress.basemodel.enums.MessageEnum;
import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 范围查询模式
 */
@AllArgsConstructor
@Getter
public enum SqlOperatorType implements OrmEnum, ValueEnum, MessageEnum {

    EQUAL(0, "="),

    IN(1, "in"),

    LIKE(2, "like"),

    ;

    private final Integer value;

    private final String message;

}
