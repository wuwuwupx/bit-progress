package com.bitprogress.basemodel.interval.bigdecimal;

import com.bitprogress.basemodel.interval.NumberInterval;
import com.bitprogress.basemodel.endpoint.interval.enums.BoundaryType;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * BigDecimal 类型端点的区间
 */
@EqualsAndHashCode(callSuper = true)
public class BigDecimalInterval extends NumberInterval<BigDecimal> {

    public BigDecimalInterval(BigDecimalLeftEndpoint leftEndpoints, BigDecimalRightEndpoint rightEndpoints) {
        super(leftEndpoints, rightEndpoints);
    }

    public BigDecimalInterval(BigDecimal leftEndpointsValue,
                              BoundaryType leftBoundaryType,
                              BigDecimal rightEndpointsValue,
                              BoundaryType rightBoundaryType) {
        super(new BigDecimalLeftEndpoint(leftEndpointsValue, leftBoundaryType),
                new BigDecimalRightEndpoint(rightEndpointsValue, rightBoundaryType));
    }

    /**
     * 获取区间间距
     */
    @Override
    protected String printValueSpan() {
        return getRightEndpointValue()
                .subtract(getLeftEndpointValue())
                .abs()
                .stripTrailingZeros()
                .toPlainString();
    }

}
