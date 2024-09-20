package com.bitprogress.basemodel.coordinate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;

/**
 * 横坐标
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
public abstract class Applicate<T extends Number> extends AbstractCoordinate<T> {

    @Serial
    private static final long serialVersionUID = 1L;

    public Applicate(T index) {
        super(index);
    }

    /**
     * 获取坐标轴类型
     */
    @Override
    public AxisType getAxisType() {
        return AxisType.DEPTH;
    }

}
