package com.bitprogress.basemodel.interval;

import com.bitprogress.basemodel.endpoint.interval.LeftIntervalEndpoint;
import com.bitprogress.basemodel.endpoint.interval.RightIntervalEndpoint;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serial;

/**
 * 抽象区间
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public abstract class NumberInterval<T extends Number> extends BasicInterval<T> {

    @Serial
    private static final long serialVersionUID = 1L;

    public NumberInterval(LeftIntervalEndpoint<T> leftEndpoint, RightIntervalEndpoint<T> rightEndpoint) {
        super(leftEndpoint, rightEndpoint);
    }

}
