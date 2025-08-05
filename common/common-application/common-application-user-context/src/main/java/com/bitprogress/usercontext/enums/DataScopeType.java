package com.bitprogress.usercontext.enums;

import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DataScopeType implements ValueEnum {

    /**
     * 全部数据
     */
    ALL(0),

    /**
     * 范围数据
     */
    SCOPE(1),

    /**
     * 自身数据
     */
    SELF(2),

    ;

    private final Integer value;

}
