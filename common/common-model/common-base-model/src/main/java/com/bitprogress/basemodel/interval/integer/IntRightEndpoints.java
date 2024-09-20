package com.bitprogress.basemodel.interval.integer;

import com.bitprogress.basemodel.interval.IntervalBoundaryType;
import com.bitprogress.basemodel.interval.RightEndpoints;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;

/**
 * 右端点
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@ToString
public class IntRightEndpoints extends RightEndpoints<Integer> {

    @Serial
    private static final long serialVersionUID = 1L;

    public IntRightEndpoints(Integer value, IntervalBoundaryType boundaryType) {
        super(value, boundaryType);
    }

    /**
     * 比较端点的值和传入的value
     *
     * @param value 传入的值
     * @return 0：相等， < 0：小于value， >0 ：大于value
     */
    @Override
    public int compareTo(Integer value) {
        return getValue().compareTo(value);
    }

}
