package com.bitprogress.basemodel.interval.bigdecimal;

import com.bitprogress.basemodel.endpoint.interval.RightIntervalEndpoint;
import com.bitprogress.basemodel.endpoint.interval.enums.BoundaryType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 右端点
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@ToString
public class BigDecimalRightEndpoint extends RightIntervalEndpoint<BigDecimal> {

    @Serial
    private static final long serialVersionUID = 1L;

    public BigDecimalRightEndpoint(BigDecimal value, BoundaryType boundaryType) {
        super(value, boundaryType);
    }

    /**
     * 比较端点的值和传入的value
     *
     * @param value 传入的值
     * @return 0：相等， < 0：小于value， >0 ：大于value
     */
    @Override
    public int valueCompareTo(BigDecimal value) {
        return getValue().compareTo(value);
    }

}
