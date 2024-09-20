package com.bitprogress.basemodel.interval.bigdecimal;

import com.bitprogress.basemodel.interval.IntervalBoundaryType;
import com.bitprogress.basemodel.interval.LeftEndpoints;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 左端点
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@ToString
public class BigDecimalLeftEndpoints extends LeftEndpoints<BigDecimal> {

    @Serial
    private static final long serialVersionUID = 1L;

    public BigDecimalLeftEndpoints(BigDecimal value, IntervalBoundaryType boundaryType) {
        super(value, boundaryType);
    }

    /**
     * 比较端点的值和传入的value
     *
     * @param value 传入的值
     * @return 0：相等， < 0：小于value， >0 ：大于value
     */
    @Override
    public int compareTo(BigDecimal value) {
        return super.getValue().compareTo(value);
    }

}
