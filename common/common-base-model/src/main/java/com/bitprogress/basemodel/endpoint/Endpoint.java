package com.bitprogress.basemodel.endpoint;

import com.bitprogress.basemodel.Point;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;

/**
 * endpoints
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@AllArgsConstructor
@ToString
public abstract class Endpoint<T> extends Point {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * interval point value
     * if value == null, then the endpoints = ∞
     */
    private final T value;

    /**
     * 比较端点的值和传入的value
     *
     * @param value 传入的值
     * @return 0：相等， < 0：小于value， >0 ：大于value
     */
    public abstract int valueCompareTo(T value);

    /**
     * 打印端点的值
     *
     * @return 展示的值
     */
    public abstract String printValue();

}
