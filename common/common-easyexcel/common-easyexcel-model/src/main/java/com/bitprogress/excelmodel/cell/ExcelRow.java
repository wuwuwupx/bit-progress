package com.bitprogress.excelmodel.cell;

import com.bitprogress.basemodel.coordinate.IntOrdinate;
import com.bitprogress.basemodel.coordinate.Ordinate;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 行坐标 -- 纵坐标
 * 用来表示 excel 操作的行
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class ExcelRow extends IntOrdinate implements ExcelRowIndex {

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
