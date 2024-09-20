package com.bitprogress.basemodel.coordinate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;

/**
 * 纵坐标
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@ToString
public abstract class Ordinate<T extends Number> extends AbstractCoordinate<T> {

    @Serial
    private static final long serialVersionUID = 1L;

    public Ordinate(T index) {
        super(index);
    }

    /**
     * 获取坐标轴类型
     */
    @Override
    public AxisType getAxisType() {
        return AxisType.VERTICAL;
    }

}
