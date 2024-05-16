package com.bitprogress.basemodel.coordinate;

import com.bitprogress.basemodel.Coordinate;
import lombok.*;

/**
 * 横坐标
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@AllArgsConstructor
@ToString
public abstract class Abscissa<T extends Number> extends Coordinate {

    /**
     * 横坐标
     */
    private final T index;

}