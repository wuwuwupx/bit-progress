package com.bitprogress.basemodel.endpoint.interval.enums;

import com.bitprogress.basemodel.enums.MessageEnum;
import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 区间边界类型，标识区间的边界类型
 */
@Getter
@AllArgsConstructor
public enum BoundaryType implements IntervalEnums, ValueEnum, MessageEnum {

    /**
     * 开区间
     */
    OPEN(0, "开区间", "(", ")"),

    /**
     * 闭区间
     */
    CLOSE(1, "闭区间", "[", "]"),

    ;

    private final Integer value;

    private final String message;

    /**
     * 区间左边界符号
     */
    private final String leftSymbols;

    /**
     * 区间右边界符号
     */
    private final String rightSymbols;

}
