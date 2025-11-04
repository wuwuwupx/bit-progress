package com.bitprogress.ormmodel.enums;

import com.bitprogress.basemodel.enums.NameEnum;
import com.bitprogress.basemodel.enums.SymbolsEnum;
import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 范围查询模式
 */
@AllArgsConstructor
@Getter
public enum SqlOperatorType implements OrmEnum, ValueEnum, NameEnum, SymbolsEnum {

    EQUAL(0, "等于", "="),

    IN(1, "包含", "in"),

    LIKE(2, "模糊匹配", "like"),

    ;

    private final Integer value;
    private final String name;
    private final String symbols;

}
