package com.bitprogress.basemodel.coordinate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;

/**
 * 纵坐标
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
public class IntOrdinate extends Ordinate<Integer> {

    @Serial
    private static final long serialVersionUID = 1L;

    public IntOrdinate(Integer index) {
        super(index);
    }

    /**
     * 打印坐标的值
     *
     * @return 展示的值
     */
    @Override
    public String printValue() {
        return getIndex().toString();
    }

}
