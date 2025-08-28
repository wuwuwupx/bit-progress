package com.bitprogress.excelcore.handler;

import com.bitprogress.excelcore.data.FreezePaneData;
import com.bitprogress.excelmodel.annotation.ExcelFreezePane;

import java.util.Objects;

/**
 * 基础写入处理器
 */
public abstract class BaseWriteHandler {

    /**
     * 获取冻结窗格处理器
     */
    public static FreezePaneHandler getFreezePaneHandler(Class<?> clazz) {
        if (Objects.isNull(clazz)) {
            return null;
        }
        ExcelFreezePane freezePane = clazz.getAnnotation(ExcelFreezePane.class);
        if (Objects.isNull(freezePane)) {
            return null;
        }
        return new FreezePaneHandler(new FreezePaneData(freezePane.colSplit(), freezePane.rowSplit(),
                freezePane.leftmostColumn(), freezePane.topRow()));
    }

}
