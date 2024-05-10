package com.bitprogress.cell;

import com.bitprogress.coordinate.Abscissa;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 列坐标 -- 横坐标
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ColumnCoordinate extends Abscissa<Integer> implements ExcelColumnIndex {

    public ColumnCoordinate(Integer columnIndex) {
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
