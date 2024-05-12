package com.bitprogress.coordinate;

import com.bitprogress.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 坐标点
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@AllArgsConstructor
public abstract class Point<T extends Number, R extends Number> extends Coordinate {

    /**
     * 横坐标
     */
    private final T abscissa;

    /**
     * 纵坐标
     */
    private final R ordinate;

    /**
     * 获取坐标字符串
     * (x,y)
     */
    public String getCoordinateString() {
        return "(" + abscissa + "," + ordinate + ")";
    }

}
