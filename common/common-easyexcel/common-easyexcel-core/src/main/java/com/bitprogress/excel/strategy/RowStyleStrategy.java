package com.bitprogress.excel.strategy;

import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.bitprogress.excelmodel.CellStyleStrategy;
import com.bitprogress.excel.style.RowWriteCellStyle;
import com.bitprogress.util.CollectionUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 行样式策略
 */
@Getter
@Setter
public class RowStyleStrategy extends HorizontalCellStyleStrategy implements CellStyleStrategy {

    /**
     * 直接采用
     */
    private final Map<Integer, WriteCellStyle> headCellStyleMap = new HashMap<>();

    /**
     * 直接采用
     */
    private final Map<Integer, WriteCellStyle> contentCellStyleMap = new HashMap<>();

    /**
     * 构造方法，创建对象时传入需要定制的表头信息队列
     */
    public RowStyleStrategy(List<RowWriteCellStyle> headCellStyles, List<RowWriteCellStyle> contentCellStyles) {
        setRowHeadCellStyle(headCellStyles);
        setRowContentCellStyle(contentCellStyles);
    }

    @Override
    protected void setHeadCellStyle(CellWriteHandlerContext context) {
        setCellStyle(context, CellType.HEAD);
    }

    @Override
    protected void setContentCellStyle(CellWriteHandlerContext context) {
        setCellStyle(context, CellType.CONTENT);
    }

    private void setCellStyle(CellWriteHandlerContext context, CellType cellType) {
        if (stopProcessing(context)) {
            return;
        }
        WriteCellData<?> cellData = context.getFirstCellData();
        Integer relativeRowIndex = context.getRelativeRowIndex();
        Map<Integer, WriteCellStyle> cellStyleMap = CellType.HEAD == cellType ? headCellStyleMap : contentCellStyleMap;
        WriteCellStyle rowCellStyle = cellStyleMap.get(relativeRowIndex);
        if (Objects.nonNull(rowCellStyle)) {
            // 设置自定义的表头样式
            WriteCellStyle.merge(rowCellStyle, cellData.getOrCreateStyle());
        }
    }

    public void setRowHeadCellStyle(List<RowWriteCellStyle> rowCellStyles) {
        if (CollectionUtils.isNotEmpty(rowCellStyles)) {
            rowCellStyles.forEach(this::setRowHeadCellStyle);
        }
    }

    public void setRowHeadCellStyle(RowWriteCellStyle rowCellStyle) {
        if (Objects.nonNull(rowCellStyle)) {
            headCellStyleMap.put(rowCellStyle.getRowIndex(), rowCellStyle.getWriteCellStyle());
        }
    }

    public void setRowContentCellStyle(List<RowWriteCellStyle> rowCellStyles) {
        if (CollectionUtils.isNotEmpty(rowCellStyles)) {
            rowCellStyles.forEach(this::setRowContentCellStyle);
        }
    }

    public void setRowContentCellStyle(RowWriteCellStyle rowCellStyle) {
        if (Objects.nonNull(rowCellStyle)) {
            contentCellStyleMap.put(rowCellStyle.getRowIndex(), rowCellStyle.getWriteCellStyle());
        }
    }

}
