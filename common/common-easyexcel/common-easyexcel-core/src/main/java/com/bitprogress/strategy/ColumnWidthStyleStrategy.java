package com.bitprogress.strategy;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import com.bitprogress.CellStyleStrategy;
import com.bitprogress.style.ColumnWidthStyle;
import com.bitprogress.util.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 列宽celue
 */
public class ColumnWidthStyleStrategy extends AbstractColumnWidthStyleStrategy implements CellStyleStrategy {

    /**
     * 列宽样式 map
     */
    private final Map<Integer, Short> columnWidthMap = new HashMap<>();

    /**
     * 默认列宽
     */
    private Short defaultColumnWidth;

    /**
     * Sets the column width when head create
     * 只针对表头设置
     * 1. 获取当前列索引的列宽数值
     * 列宽数值不为空则设置，列宽数值为空则获取默认列宽
     */
    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder,
                                  List<WriteCellData<?>> cellDataList,
                                  Cell cell,
                                  Head head,
                                  Integer relativeRowIndex,
                                  Boolean isHead) {
        if (!isHead) {
            return;
        }
        Integer columnIndex = head.getColumnIndex();
        Short columnWidth = columnWidthMap.get(columnIndex);
        if (Objects.nonNull(columnWidth)) {
            setColumnWidth(writeSheetHolder, cell, columnWidth);
        } else {
            if (Objects.nonNull(this.defaultColumnWidth)) {
                setColumnWidth(writeSheetHolder, cell, this.defaultColumnWidth);
            }
        }
    }

    /**
     * 设置列宽，需要 * 256
     */
    private void setColumnWidth(WriteSheetHolder writeSheetHolder, Cell cell, Short columnWidth) {
        int width = columnWidth * 256;
        writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), width);
    }

    public void setColumnWidth(ColumnWidthStyle columnWidthStyle) {
        if (Objects.isNull(columnWidthStyle)) {
            return;
        }
        columnWidthMap.put(columnWidthStyle.getColumnIndex(), columnWidthStyle.getColumnWidth());
    }

    public void setColumnWidth(Integer columnIndex, Short columnWidth) {
        columnWidthMap.put(columnIndex, columnWidth);
    }

    public void setColumnWidth(List<ColumnWidthStyle> columnWidthStyles) {
        if (CollectionUtils.isEmpty(columnWidthStyles)) {
            return;
        }
        columnWidthStyles.forEach(this::setColumnWidth);
    }

    public Short getDefaultColumnWidth() {
        return defaultColumnWidth;
    }

    public void setDefaultColumnWidth(Short defaultColumnWidth) {
        this.defaultColumnWidth = defaultColumnWidth;
    }

}
