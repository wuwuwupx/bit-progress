package com.bitprogress.basemodel.interval.integer;

import com.bitprogress.basemodel.endpoint.interval.LeftIntervalEndpoint;
import com.bitprogress.basemodel.endpoint.interval.enums.BoundaryType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;

/**
 * 左端点
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@ToString
public class IntLeftEndpoint extends LeftIntervalEndpoint<Integer> {

    @Serial
    private static final long serialVersionUID = 1L;

    public IntLeftEndpoint(Integer value, BoundaryType boundaryType) {
        super(value, boundaryType);
    }

    /**
     * 比较端点的值和传入的value
     *
     * @param value 传入的值
     * @return 0：相等， < 0：小于value， >0 ：大于value
     */
    @Override
    public int valueCompareTo(Integer value) {
        return super.getValue().compareTo(value);
    }

}
