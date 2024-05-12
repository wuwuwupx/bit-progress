package com.bitprogress.cell;

import com.bitprogress.coordinate.Ordinate;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 行坐标 -- 纵坐标
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class ExcelRow extends Ordinate<Integer> implements ExcelRowIndex {

    public ExcelRow(Integer rowIndex) {
        super(rowIndex);
    }

    /**
     * 获取行索引
     */
    @Override
    public Integer getRowIndex() {
        return getIndex();
    }

}
