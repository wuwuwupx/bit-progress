package com.bitprogress.excelmodel.cell;

import com.bitprogress.basemodel.coordinate.Abscissa;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 列坐标 -- 横坐标
 * 用来表示excel 操作的列
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class ExcelColumn extends Abscissa<Integer> implements ExcelColumnIndex {

    public ExcelColumn(Integer columnIndex) {
        super(columnIndex);
    }

    /**
     * 获取列索引
     */
    @Override
    public Integer getColumnIndex() {
        return getIndex();
    }

}
