package com.bitprogress.basemodel.coordinate;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serial;

/**
 * 坐标点
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public class IntPoint2D extends AbstractPoint2D<Integer> {

    @Serial
    private static final long serialVersionUID = 1L;

    public IntPoint2D(IntAbscissa abscissa, IntOrdinate ordinate) {
        super(abscissa, ordinate);
    }

}
