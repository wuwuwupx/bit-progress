package com.bitprogress.basemodel.endpoint.interval.enums;

import com.bitprogress.basemodel.enums.MessageEnum;
import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 端点类型
 */
@Getter
@AllArgsConstructor
public enum EndpointType implements IntervalEnums, ValueEnum, MessageEnum {

    /**
     * 区间开始的端点
     */
    LEFT(0, "左端点"),

    /**
     * 区间结束的端点
     */
    RIGHT(1, "右端点"),

    ;

    private final Integer value;

    private final String message;

}
