package com.bitprogress.cell;

import com.bitprogress.coordinate.Ordinate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 行坐标 -- 纵坐标
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RowCoordinate extends Ordinate<Integer> implements ExcelRowIndex {

    public RowCoordinate(Integer rowIndex) {
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
