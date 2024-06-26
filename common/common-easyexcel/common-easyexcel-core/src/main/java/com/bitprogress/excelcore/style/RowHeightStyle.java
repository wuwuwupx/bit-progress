package com.bitprogress.excelcore.style;

import com.bitprogress.excelmodel.ExcelStyle;
import com.bitprogress.excelmodel.cell.ExcelRow;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 行高样式
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class RowHeightStyle extends ExcelRow implements ExcelStyle {

    /**
     * 行高
     */
    private Short rowHeight;

    public RowHeightStyle(Integer rowIndex) {
        super(rowIndex);
    }

    public RowHeightStyle(Integer rowIndex, Short rowHeight) {
        super(rowIndex);
        this.rowHeight = rowHeight;
    }

}
