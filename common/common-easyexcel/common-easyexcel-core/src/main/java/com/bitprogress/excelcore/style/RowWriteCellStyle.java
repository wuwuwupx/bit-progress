package com.bitprogress.excelcore.style;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.bitprogress.excelmodel.ExcelStyle;
import com.bitprogress.excelmodel.cell.ExcelRow;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 复杂表头样式信息，包含需要自定义的表头坐标及样式
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class RowWriteCellStyle extends ExcelRow implements ExcelStyle {

    private WriteCellStyle writeCellStyle;

    public RowWriteCellStyle(Integer rowIndex) {
        super(rowIndex);
    }

    public RowWriteCellStyle(Integer rowIndex, WriteCellStyle writeCellStyle) {
        super(rowIndex);
        this.writeCellStyle = writeCellStyle;
    }

}