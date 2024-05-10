package com.bitprogress.cell;

import com.bitprogress.coordinate.Point;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 单元格坐标 -- 点
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CellCoordinate extends Point<Integer, Integer> implements ExcelCellIndex {

    /**
     * 强制输入坐标
     *
     * @param rowIndex    行索引
     * @param columnIndex 列索引
     */
    public CellCoordinate(Integer rowIndex, Integer columnIndex) {
        super(columnIndex, rowIndex);
    }

    /**
     * 获取行索引
     */
    @Override
    public Integer getRowIndex() {
        return getOrdinate();
    }

    /**
     * 获取列索引
     */
    @Override
    public Integer getColumnIndex() {
        return getAbscissa();
    }
}
