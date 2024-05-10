package com.bitprogress.coordinate;

import com.bitprogress.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 横坐标
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Abscissa<T extends Number> extends Coordinate {

    /**
     * 横坐标
     */
    private T index;

}
