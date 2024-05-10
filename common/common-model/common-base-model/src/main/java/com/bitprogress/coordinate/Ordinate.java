package com.bitprogress.coordinate;

import com.bitprogress.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 纵坐标
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Ordinate<T extends Number> extends Coordinate {

    /**
     * 行索引
     */
    private T index;

}
