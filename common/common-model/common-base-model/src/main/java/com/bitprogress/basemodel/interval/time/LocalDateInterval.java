package com.bitprogress.basemodel.interval.time;

import com.bitprogress.basemodel.endpoint.interval.enums.BoundaryType;
import com.bitprogress.basemodel.interval.BasicInterval;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Integer 类型端点的区间
 */
@EqualsAndHashCode(callSuper = true)
public class LocalDateInterval extends BasicInterval<LocalDate> {

    public LocalDateInterval(LocalDateLeftEndpoint leftEndpoints, LocalDateRightEndpoint rightEndpoints) {
        super(leftEndpoints, rightEndpoints);
    }

    public LocalDateInterval(LocalDate leftEndpointsValue,
                             BoundaryType leftBoundaryType,
                             LocalDate rightEndpointsValue,
                             BoundaryType rightBoundaryType) {
        super(new LocalDateLeftEndpoint(leftEndpointsValue, leftBoundaryType),
                new LocalDateRightEndpoint(rightEndpointsValue, rightBoundaryType));
    }

    /**
     * 获取区间间距
     */
    @Override
    protected String printValueSpan() {
        return String.valueOf(Math.abs(getRightEndpointValue().until(getLeftEndpointValue(), ChronoUnit.DAYS)));
    }

}
