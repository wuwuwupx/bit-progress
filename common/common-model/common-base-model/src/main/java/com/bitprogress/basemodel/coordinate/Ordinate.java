package com.bitprogress.basemodel.coordinate;

import com.bitprogress.basemodel.Point;
import lombok.*;

import java.io.Serial;

/**
 * 纵坐标
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@AllArgsConstructor
@ToString
public abstract class Ordinate<T extends Number> extends Point {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 行索引
     */
    private final T index;

}
