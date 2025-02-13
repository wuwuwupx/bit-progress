package com.bitprogress.basemodel.interval.time;

import com.bitprogress.basemodel.endpoint.interval.enums.BoundaryType;
import com.bitprogress.basemodel.interval.BasicInterval;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Integer 类型端点的区间
 */
@EqualsAndHashCode(callSuper = true)
public class LocalDateTimeInterval extends BasicInterval<LocalDateTime> {

    public LocalDateTimeInterval(LocalDateTimeLeftEndpoint leftEndpoints, LocalDateTimeRightEndpoint rightEndpoints) {
        super(leftEndpoints, rightEndpoints);
    }

    public LocalDateTimeInterval(LocalDateTime leftEndpointsValue,
                                 BoundaryType leftBoundaryType,
                                 LocalDateTime rightEndpointsValue,
                                 BoundaryType rightBoundaryType) {
        super(new LocalDateTimeLeftEndpoint(leftEndpointsValue, leftBoundaryType),
                new LocalDateTimeRightEndpoint(rightEndpointsValue, rightBoundaryType));
    }

    /**
     * 获取区间间距
     */
    @Override
    protected String printValueSpan() {
        return String.valueOf(getRightEndpointValue().until(getLeftEndpointValue(), ChronoUnit.NANOS));
    }

}
