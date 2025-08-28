package com.bitprogress.excelcore.handler;

import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Excel写处理器
 *
 * @author yanjiaxin
 * @date 2023/12/25
 */
@Slf4j
public class ExcelWriteHandler extends BaseWriteHandler {

    /**
     * 写入自定义处理器
     */
    public static void registerWriteHandler(ExcelWriterSheetBuilder sheetBuilder, Class<?> clazz) {
        FreezePaneHandler freezePaneHandler = getFreezePaneHandler(clazz);
        if (Objects.nonNull(freezePaneHandler)) {
            sheetBuilder.registerWriteHandler(freezePaneHandler);
        }
    }

}
