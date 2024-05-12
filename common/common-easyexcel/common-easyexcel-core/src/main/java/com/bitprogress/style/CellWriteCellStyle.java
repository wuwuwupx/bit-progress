package com.bitprogress.style;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.bitprogress.ExcelStyle;
import com.bitprogress.cell.ExcelCell;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 单元格样式
 * 针对某一单元格
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class CellWriteCellStyle extends ExcelCell implements ExcelStyle {

    /**
     * 样式
     */
    private WriteCellStyle writeCellStyle;

    public CellWriteCellStyle(Integer rowIndex, Integer columnIndex) {
        super(rowIndex, columnIndex);
    }

    public CellWriteCellStyle(Integer rowIndex, Integer columnIndex, WriteCellStyle writeCellStyle) {
        super(rowIndex, columnIndex);
        this.writeCellStyle = writeCellStyle;
    }

}
