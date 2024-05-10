package com.bitprogress.style;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.bitprogress.cell.CellCoordinate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class CoordinateWriteCellStyle extends CellCoordinate {

    /**
     * 样式
     */
    private WriteCellStyle writeCellStyle;

    public CoordinateWriteCellStyle(Integer rowIndex, Integer columnIndex) {
        super(rowIndex, columnIndex);
    }

    public CoordinateWriteCellStyle(Integer rowIndex, Integer columnIndex, WriteCellStyle writeCellStyle) {
        super(rowIndex, columnIndex);
        this.writeCellStyle = writeCellStyle;
    }

}
