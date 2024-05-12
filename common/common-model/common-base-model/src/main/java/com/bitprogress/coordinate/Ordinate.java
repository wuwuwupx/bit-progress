package com.bitprogress.coordinate;

import com.bitprogress.Coordinate;
import lombok.*;

/**
 * 纵坐标
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@AllArgsConstructor
@ToString
public abstract class Ordinate<T extends Number> extends Coordinate {

    /**
     * 行索引
     */
    private final T index;

}
