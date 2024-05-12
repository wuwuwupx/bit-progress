package com.bitprogress.style;

import com.bitprogress.ExcelStyle;
import com.bitprogress.cell.ExcelColumn;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 列宽样式
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class ColumnWidthStyle extends ExcelColumn implements ExcelStyle {

    /**
     * 列宽
     */
    private Short columnWidth;

    public ColumnWidthStyle(Integer columnIndex) {
        super(columnIndex);
    }

    public ColumnWidthStyle(Integer columnIndex, Short columnWidth) {
        super(columnIndex);
        this.columnWidth = columnWidth;
    }

}
