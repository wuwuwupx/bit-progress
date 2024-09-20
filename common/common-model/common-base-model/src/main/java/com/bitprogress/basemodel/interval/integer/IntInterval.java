package com.bitprogress.basemodel.interval.integer;

import com.bitprogress.basemodel.interval.AbstractInterval;
import com.bitprogress.basemodel.interval.IntervalBoundaryType;
import lombok.EqualsAndHashCode;

/**
 * Integer 类型端点的区间
 */
@EqualsAndHashCode(callSuper = true)
public class IntInterval extends AbstractInterval<Integer> {

    public IntInterval(IntLeftEndpoints leftEndpoints, IntRightEndpoints rightEndpoints) {
        super(leftEndpoints, rightEndpoints);
    }

    public IntInterval(Integer leftEndpointsValue,
                       IntervalBoundaryType leftBoundaryType,
                       Integer rightEndpointsValue,
                       IntervalBoundaryType rightBoundaryType) {
        super(new IntLeftEndpoints(leftEndpointsValue, leftBoundaryType),
                new IntRightEndpoints(rightEndpointsValue, rightBoundaryType));
    }

}
