package com.bitprogress.basemodel.interval.integer;

import com.bitprogress.basemodel.interval.NumberInterval;
import com.bitprogress.basemodel.endpoint.interval.enums.BoundaryType;
import lombok.EqualsAndHashCode;

/**
 * Integer 类型端点的区间
 */
@EqualsAndHashCode(callSuper = true)
public class IntInterval extends NumberInterval<Integer> {

    public IntInterval(IntLeftEndpoint leftEndpoints, IntRightEndpoint rightEndpoints) {
        super(leftEndpoints, rightEndpoints);
    }

    public IntInterval(Integer leftEndpointsValue,
                       BoundaryType leftBoundaryType,
                       Integer rightEndpointsValue,
                       BoundaryType rightBoundaryType) {
        super(new IntLeftEndpoint(leftEndpointsValue, leftBoundaryType),
                new IntRightEndpoint(rightEndpointsValue, rightBoundaryType));
    }

}
