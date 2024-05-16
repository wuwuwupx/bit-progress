package com.bitprogress.excelmodel.cell;

import com.bitprogress.basemodel.coordinate.Point;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 单元格坐标 -- 点
 * 用来表示excel 操作的单元格
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class ExcelCell extends Point<Integer, Integer> implements ExcelCellIndex {

    /**
     * 强制输入坐标
     *
     * @param rowIndex    行索引
     * @param columnIndex 列索引
     */
    public ExcelCell(Integer rowIndex, Integer columnIndex) {
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
