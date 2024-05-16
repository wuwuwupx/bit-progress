package com.bitprogress.excel.strategy;

import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.bitprogress.excelmodel.CellStyleStrategy;
import com.bitprogress.excel.style.CellWriteCellStyle;
import com.bitprogress.util.CollectionUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 单元格样式策略
 *
 * @author wpx
 */
@Getter
@Setter
public class CoordinateStyleStrategy extends HorizontalCellStyleStrategy implements CellStyleStrategy {

    /**
     * 自定义样式，行坐标-纵坐标  {row-column}
     */
    private final Map<String, WriteCellStyle> headCellStyleMap = new HashMap<>();

    /**
     * 自定义样式，行坐标-纵坐标  {row-column}
     */
    private final Map<String, WriteCellStyle> contentCellStyleMap = new HashMap<>();

    /**
     * 构造方法，创建对象时传入需要定制的表头信息队列
     */
    public CoordinateStyleStrategy(List<CellWriteCellStyle> headCellStyles,
                                   List<CellWriteCellStyle> contentCellStyles) {
        setHeadWriteCellStyle(headCellStyles);
        setContentWriteCellStyle(contentCellStyles);
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
        Integer columnIndex = context.getColumnIndex();
        // (x,y)，横坐标为 column
        String coordinate =  "(" + columnIndex + "," + relativeRowIndex + ")";
        boolean isHead = Objects.equals(CellType.HEAD, cellType);
        Map<String, WriteCellStyle> cellStyleMap = isHead ? headCellStyleMap : contentCellStyleMap;
        WriteCellStyle coordinateCellStyle = CollectionUtils.getForMap(cellStyleMap, coordinate);
        if (Objects.nonNull(coordinateCellStyle)) {
            // 设置自定义的表头样式
            WriteCellStyle.merge(coordinateCellStyle, cellData.getOrCreateStyle());
        }
    }

    public void setHeadWriteCellStyle(List<CellWriteCellStyle> headCellStyles) {
        if (CollectionUtils.isNotEmpty(headCellStyles)) {
            headCellStyles.forEach(this::setHeadWriteCellStyle);
        }
    }

    public void setHeadWriteCellStyle(CellWriteCellStyle headCellStyle) {
        if (Objects.nonNull(headCellStyle)) {
            headCellStyleMap.put(headCellStyle.getCoordinateString(), headCellStyle.getWriteCellStyle());
        }
    }

    public void setContentWriteCellStyle(List<CellWriteCellStyle> contentCellStyles) {
        if (CollectionUtils.isNotEmpty(contentCellStyles)) {
            contentCellStyles.forEach(this::setContentWriteCellStyle);
        }
    }

    public void setContentWriteCellStyle(CellWriteCellStyle contentCellStyle) {
        if (Objects.nonNull(contentCellStyle)) {
            contentCellStyleMap.put(contentCellStyle.getCoordinateString(), contentCellStyle.getWriteCellStyle());
        }
    }

}
