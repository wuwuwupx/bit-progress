package com.bitprogress.excel.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.bitprogress.excel.data.FreezePaneData;
import org.apache.poi.ss.usermodel.Sheet;

public class FreezePaneHandler implements SheetWriteHandler {

    private final FreezePaneData freezePaneData;

    public FreezePaneData getFreezePaneData() {
        return freezePaneData;
    }

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

    public FreezePaneHandler(FreezePaneData freezePaneData) {
        this.freezePaneData = freezePaneData;
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
