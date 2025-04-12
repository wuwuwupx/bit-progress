package com.bitprogress.ormmodel.enums;

import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataScopeMode implements OrmEnum, ValueEnum {

    /**
     * 单类型数据范围
     */
    SINGLE(0),

    /**
     * 多重类型数据范围
     */
    MULTIPLE(1),

    /**
     * 组合类型数据范围
     */
    COMBINATION(2),

    ;

    private final Integer value;

}
