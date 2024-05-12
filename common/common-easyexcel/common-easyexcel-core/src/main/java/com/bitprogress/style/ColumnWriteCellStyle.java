package com.bitprogress.style;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.bitprogress.ExcelStyle;
import com.bitprogress.cell.ExcelColumn;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 列样式
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class ColumnWriteCellStyle extends ExcelColumn implements ExcelStyle {

    private WriteCellStyle writeCellStyle;

    public ColumnWriteCellStyle(Integer rowIndex) {
        super(rowIndex);
    }

    public ColumnWriteCellStyle(Integer rowIndex, WriteCellStyle writeCellStyle) {
        super(rowIndex);
        this.writeCellStyle = writeCellStyle;
    }

}