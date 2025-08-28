package com.bitprogress.excelcore.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.bitprogress.excelcore.data.FreezePaneData;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 冻结窗格处理器
 */
public record FreezePaneHandler(FreezePaneData freezePaneData) implements SheetWriteHandler {

    public Integer getColSplit() {
        return freezePaneData.getColSplit();
    }

    public Integer getRowSplit() {
        return freezePaneData.getRowSplit();
    }

    public Integer getLeftmostColumn() {
        return freezePaneData.getLeftmostColumn();
    }

    public Integer getTopRow() {
        return freezePaneData.getTopRow();
    }

    /**
     * Called after the sheet is created
     */
    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();
        sheet.createFreezePane(getColSplit(), getRowSplit(), getLeftmostColumn(), getTopRow());
    }

}
