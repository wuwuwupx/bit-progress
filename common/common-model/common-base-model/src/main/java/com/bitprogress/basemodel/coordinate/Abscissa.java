package com.bitprogress.basemodel.coordinate;

import com.bitprogress.basemodel.coordinate.enums.AxisType;
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
public abstract class Abscissa<T extends Number> extends AbstractCoordinate<T> {

    @Serial
    private static final long serialVersionUID = 1L;

    public Abscissa(T index) {
        super(index);
    }

    /**
     * 获取坐标轴类型
     */
    @Override
    public AxisType getAxisType() {
        return AxisType.HORIZONTAL;
    }

}
