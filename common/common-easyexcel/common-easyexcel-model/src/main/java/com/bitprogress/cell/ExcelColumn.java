package com.bitprogress.cell;

import com.bitprogress.coordinate.Abscissa;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 列坐标 -- 横坐标
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
