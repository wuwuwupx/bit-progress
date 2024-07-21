package com.bitprogress.basemodel.coordinate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serial;

/**
 * 抽象坐标点
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@AllArgsConstructor
public abstract class AbstractCoordinate<T extends Number, R extends Number> extends Coordinate {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 横坐标
     */
    private final Abscissa<T> abscissa;

    /**
     * 纵坐标
     */
    private final Ordinate<R> ordinate;

    /**
     * 获取横坐标索引
     */
    public T getAbscissaIndex() {
        return abscissa.getIndex();
    }

    /**
     * 获取纵坐标索引
     */
    public R getOrdinateIndex() {
        return ordinate.getIndex();
    }

    /**
     * 获取坐标字符串
     * (x,y)
     */
    public String getCoordinateString() {
        return "(" + abscissa.getIndex() + "," + ordinate.getIndex() + ")";
    }

}
