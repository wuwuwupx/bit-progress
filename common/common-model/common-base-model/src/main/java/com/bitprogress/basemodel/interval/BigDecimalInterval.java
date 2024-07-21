package com.bitprogress.basemodel.interval;

import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * BigDecimal 类型端点的区间
 */
@EqualsAndHashCode(callSuper = true)
public class BigDecimalInterval extends AbstractInterval<BigDecimal> {

    public BigDecimalInterval(BigDecimalLeftEndpoints leftEndpoints, BigDecimalRightEndpoints rightEndpoints) {
        super(leftEndpoints, rightEndpoints);
    }

    public BigDecimalInterval(BigDecimal leftEndpointsValue,
                              IntervalBoundaryType leftBoundaryType,
                              BigDecimal rightEndpointsValue,
                              IntervalBoundaryType rightBoundaryType) {
        super(new BigDecimalLeftEndpoints(leftEndpointsValue, leftBoundaryType),
                new BigDecimalRightEndpoints(rightEndpointsValue, rightBoundaryType));
    }

}
