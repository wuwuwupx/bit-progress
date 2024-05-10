package com.bitprogress.coordinate;

import com.bitprogress.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 坐标点
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Point<T extends Number, R extends Number> extends Coordinate {

    /**
     * 横坐标
     */
    private T abscissa;

    /**
     * 纵坐标
     */
    private R ordinate;

}
