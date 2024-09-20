package com.bitprogress.basemodel.interval.integer;

import com.bitprogress.basemodel.interval.AbstractInterval;
import com.bitprogress.basemodel.interval.IntervalBoundaryType;
import lombok.EqualsAndHashCode;

/**
 * Integer 类型端点的区间
 */
@EqualsAndHashCode(callSuper = true)
public class IntegerInterval extends AbstractInterval<Integer> {

    public IntegerInterval(IntegerLeftEndpoints leftEndpoints, IntegerRightEndpoints rightEndpoints) {
        super(leftEndpoints, rightEndpoints);
    }

    public IntegerInterval(Integer leftEndpointsValue,
                           IntervalBoundaryType leftBoundaryType,
                           Integer rightEndpointsValue,
                           IntervalBoundaryType rightBoundaryType) {
        super(new IntegerLeftEndpoints(leftEndpointsValue, leftBoundaryType),
                new IntegerRightEndpoints(rightEndpointsValue, rightBoundaryType));
    }

}
