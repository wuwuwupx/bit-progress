package com.bitprogress.strategy;

import com.alibaba.excel.write.style.row.AbstractRowHeightStyleStrategy;
import com.bitprogress.CellStyleStrategy;
import com.bitprogress.style.RowHeightStyle;
import com.bitprogress.util.CollectionUtils;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 行高策略
 * 分表头行高和内容行高
 */
public class RowHeightStyleStrategy extends AbstractRowHeightStyleStrategy implements CellStyleStrategy {

    private final Map<Integer, Short> headRowHeight = new HashMap<>();

    private final Map<Integer, Short> contentRowHeight = new HashMap<>();

    private Short defaultContentRowHeight;

    /**
     * Sets the height of header
     *
     * @param row
     * @param relativeRowIndex
     */
    @Override
    protected void setHeadColumnHeight(Row row, int relativeRowIndex) {
        Short height = headRowHeight.get(relativeRowIndex);
        if (Objects.nonNull(height)) {
            row.setHeightInPoints(height);
        }
    }

    /**
     * Sets the height of content
     */
    @Override
    protected void setContentColumnHeight(Row row, int relativeRowIndex) {
        Short height = contentRowHeight.get(relativeRowIndex);
        if (Objects.nonNull(height)) {
            row.setHeightInPoints(height);
        } else {
            if (Objects.nonNull(this.defaultContentRowHeight)) {
                row.setHeightInPoints(defaultContentRowHeight);
            }
        }
    }

    public void setHeadRowHeight(RowHeightStyle rowHeightStyle) {
        if (Objects.isNull(rowHeightStyle)) {
            return;
        }
        headRowHeight.put(rowHeightStyle.getRowIndex(), rowHeightStyle.getRowHeight());
    }

    public void setHeadRowHeight(Integer rowIndex, Short rowHeight) {
        headRowHeight.put(rowIndex, rowHeight);
    }

    public void setHeadRowHeight(List<RowHeightStyle> rowHeightStyles) {
        if (CollectionUtils.isEmpty(rowHeightStyles)) {
            return;
        }
        rowHeightStyles.forEach(this::setHeadRowHeight);
    }

    public void setContentRowHeight(RowHeightStyle rowHeightStyle) {
        if (Objects.isNull(rowHeightStyle)) {
            return;
        }
        contentRowHeight.put(rowHeightStyle.getRowIndex(), rowHeightStyle.getRowHeight());
    }

    public void setContentRowHeight(Integer rowIndex, Short rowHeight) {
        contentRowHeight.put(rowIndex, rowHeight);
    }

    public void setContentRowHeight(List<RowHeightStyle> rowHeightStyles) {
        if (Objects.isNull(rowHeightStyles)) {
            return;
        }
        rowHeightStyles.forEach(this::setContentRowHeight);
    }

    public Short getDefaultContentRowHeight() {
        return defaultContentRowHeight;
    }

    public void setDefaultContentRowHeight(Short defaultContentRowHeight) {
        this.defaultContentRowHeight = defaultContentRowHeight;
    }

}
