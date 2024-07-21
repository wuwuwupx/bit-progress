package com.bitprogress.basemodel.coordinate;

import com.bitprogress.basemodel.Point;
import lombok.*;

import java.io.Serial;

/**
 * 横坐标
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@AllArgsConstructor
@ToString
public abstract class Abscissa<T extends Number> extends Point {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 横坐标
     */
    private final T index;

}
