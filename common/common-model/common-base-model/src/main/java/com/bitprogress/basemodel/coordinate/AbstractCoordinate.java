package com.bitprogress.basemodel.coordinate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Getter
@AllArgsConstructor
@ToString
public abstract class AbstractCoordinate<T extends Number> extends Coordinate {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 坐标值
     */
    private final T index;

    /**
     * 获取坐标轴类型
     */
    public abstract AxisType getAxisType();

    /**
     * 打印坐标的值
     *
     * @return 坐标值
     */
    public abstract String printValue();

}
