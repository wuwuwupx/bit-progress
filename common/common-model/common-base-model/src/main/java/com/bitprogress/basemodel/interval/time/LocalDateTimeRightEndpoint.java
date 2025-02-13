package com.bitprogress.basemodel.interval.time;

import com.bitprogress.basemodel.endpoint.interval.RightIntervalEndpoint;
import com.bitprogress.basemodel.endpoint.interval.enums.BoundaryType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 右端点
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@ToString
public class LocalDateTimeRightEndpoint extends RightIntervalEndpoint<LocalDateTime> {

    @Serial
    private static final long serialVersionUID = 1L;

    public LocalDateTimeRightEndpoint(LocalDateTime value, BoundaryType boundaryType) {
        super(value, boundaryType);
    }

    /**
     * 比较端点的值和传入的value
     *
     * @param value 传入的值
     * @return 0：相等， < 0：小于value， >0 ：大于value
     */
    @Override
    public int valueCompareTo(LocalDateTime value) {
        return getValue().compareTo(value);
    }

}
