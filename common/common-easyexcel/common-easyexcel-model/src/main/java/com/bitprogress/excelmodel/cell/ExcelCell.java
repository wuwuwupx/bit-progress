package com.bitprogress.excelmodel.cell;

import com.bitprogress.basemodel.coordinate.IntAbscissa;
import com.bitprogress.basemodel.coordinate.IntOrdinate;
import com.bitprogress.basemodel.coordinate.IntPoint2D;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 单元格坐标 -- 点
 * 用来表示excel 操作的单元格
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class ExcelCell extends IntPoint2D implements ExcelCellIndex {

    /**
     * 强制输入坐标
     *
     * @param rowIndex    行索引
     * @param columnIndex 列索引
     */
    public ExcelCell(Integer rowIndex, Integer columnIndex) {
        this(new IntAbscissa(columnIndex), new IntOrdinate(rowIndex));
    }

    /**
     * 强制输入坐标
     *
     * @param abscissa 行索引
     * @param ordinate 列索引
     */
    public ExcelCell(IntAbscissa abscissa, IntOrdinate ordinate) {
        super(abscissa, ordinate);
    }

    /**
     * 获取行索引
     */
    @Override
    public Integer getRowIndex() {
        return getOrdinateIndex();
    }

    /**
     * 获取列索引
     */
    @Override
    public Integer getColumnIndex() {
        return getAbscissaIndex();
    }
}
