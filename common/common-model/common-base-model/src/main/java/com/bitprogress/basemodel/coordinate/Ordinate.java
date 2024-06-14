package com.bitprogress.basemodel.coordinate;

import com.bitprogress.basemodel.Coordinate;
import lombok.*;

import java.io.Serial;

/**
 * 纵坐标
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@AllArgsConstructor
@ToString
public abstract class Ordinate<T extends Number> extends Coordinate {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 行索引
     */
    private final T index;

}
