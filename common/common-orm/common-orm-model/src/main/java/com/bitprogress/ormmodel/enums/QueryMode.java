package com.bitprogress.ormmodel.enums;

import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 查询模式
 */
@Getter
@AllArgsConstructor
public enum QueryMode implements OrmEnum, ValueEnum {

    /**
     * 全链路查询
     */
    FULL_CHAIN(0),

    /**
     * 上游链路查询
     */
    UPSTREAM_CHAIN(1),

    /**
     * 下游链路查询
     */
    DOWNSTREAM_CHAIN(2),

    ;

    private final Integer value;

}
