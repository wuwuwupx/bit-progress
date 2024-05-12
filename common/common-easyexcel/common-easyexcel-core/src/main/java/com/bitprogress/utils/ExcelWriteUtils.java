package com.bitprogress.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.bitprogress.data.WriteSheetData;
import com.bitprogress.util.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

public class ExcelWriteUtils {

    /**
     * 导出信息
     *
     * @param list 导出信息
     */
    public static <T> byte[] createExcelByte(List<T> list, Class<T> tClass, String sheetName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        exportExcel(outputStream, list, tClass, sheetName);
        return outputStream.toByteArray();
    }

    /**
     * 导出信息
     *
     * @param outputStream 输出流
     * @param list         导出信息
     */
    public static <T> void exportExcel(OutputStream outputStream, List<T> list, Class<T> tClass, String sheetName) {
        ExcelWriter excelWriter = EasyExcel.write(outputStream, tClass).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
        // 这里注意 如果同一个sheet只要创建一次
        excelWriter.write(list, writeSheet);
        excelWriter.finish();
    }

    /**
     * 导出信息
     *
     * @param outputStream 输出流
     * @param list         导出信息
     */
    public static <T, R extends WriteHandler> void exportExcel(OutputStream outputStream,
                                                               List<T> list,
                                                               Class<T> tClass,
                                                               String sheetName,
                                                               List<R> writeHandlers) {
        ExcelWriter excelWriter = EasyExcel.write(outputStream, tClass).build();
        ExcelWriterSheetBuilder builder = EasyExcel.writerSheet(sheetName);
        if (CollectionUtils.isNotEmpty(writeHandlers)) {
            writeHandlers.forEach(builder::registerWriteHandler);
        }
        WriteSheet writeSheet = builder.build();
        // 这里注意 如果同一个sheet只要创建一次
        excelWriter.write(list, writeSheet);
        excelWriter.finish();
    }

    /**
     * 导出信息
     *
     * @param outputStream 输出流
     * @param list         导出信息
     */
    @SafeVarargs
    public static <T, R extends WriteHandler> void exportExcel(OutputStream outputStream,
                                                               List<T> list,
                                                               Class<T> tClass,
                                                               String sheetName,
                                                               R... writeHandlers) {
        ExcelWriter excelWriter = EasyExcel.write(outputStream, tClass).build();
        ExcelWriterSheetBuilder builder = EasyExcel.writerSheet(sheetName);
        for (R writeHandler : writeHandlers) {
            if (Objects.nonNull(writeHandler)) {
                builder.registerWriteHandler(writeHandler);
            }
        }
        WriteSheet writeSheet = builder.build();
        // 这里注意 如果同一个sheet只要创建一次
        excelWriter.write(list, writeSheet);
        excelWriter.finish();
    }

    /**
     * 导出信息
     *
     * @param outputStream   输出流
     * @param writeSheetData 数据表
     */
    public static void exportExcel(OutputStream outputStream, WriteSheetData... writeSheetData) {
        ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
        exportExcel(excelWriter, writeSheetData);
    }

    /**
     * 导出数据到模板excel中
     *
     * @param outputStream 输出流
     * @param templateFile 输入流（即模板文件）
     * @param excelType    excel类型
     * @param data         数据列表
     * @param tClass       数据类型（为空则标识不追加表头）
     * @param sheetName    工作表名称，模板中有同名 sheet则追加
     */
    public static <T> void exportTemplateExcel(OutputStream outputStream,
                                               File templateFile,
                                               ExcelTypeEnum excelType,
                                               List<T> data,
                                               Class<T> tClass,
                                               String sheetName) {
        WriteSheet sheet = getSheet(tClass, null, sheetName);
        ExcelWriter writer = EasyExcel.write()
                .withTemplate(templateFile)
                .file(outputStream)
                .excelType(excelType)
                .build();
        exportExcel(writer, sheet, data);
    }

    /**
     * 导出数据到模板excel中
     *
     * @param outputStream        输出流
     * @param templateInputStream 输入流（即模板文件）
     * @param excelType           excel类型
     * @param list                数据列表
     * @param tClass              数据类型（为空则标识不追加表头）
     * @param sheetName           工作表名称，模板中有同名 sheet则追加
     */
    public static <T> void exportTemplateExcel(OutputStream outputStream,
                                               InputStream templateInputStream,
                                               ExcelTypeEnum excelType,
                                               List<T> list,
                                               Class<T> tClass,
                                               String sheetName) {
        exportTemplateExcel(outputStream, templateInputStream, excelType, list, tClass, null, sheetName);
    }

    /**
     * 导出数据到模板excel中
     *
     * @param outputStream        输出流
     * @param templateInputStream 输入流（即模板文件）
     * @param excelType           excel类型
     * @param data                数据列表
     * @param tClass              数据类型（为空则标识不追加表头）
     * @param sheetName           工作表名称，模板中有同名 sheet则追加
     */
    public static <T> void exportTemplateExcel(OutputStream outputStream,
                                               InputStream templateInputStream,
                                               ExcelTypeEnum excelType,
                                               List<T> data,
                                               Class<T> tClass,
                                               Integer sheetNo,
                                               String sheetName) {
        WriteSheet sheet = getSheet(tClass, sheetNo, sheetName);
        ExcelWriter writer = EasyExcel.write()
                .withTemplate(templateInputStream)
                .file(outputStream)
                .excelType(excelType)
                .build();
        exportExcel(writer, sheet, data);
    }

    /**
     * 导出数据到模板excel中
     *
     * @param outputStream        输出流
     * @param templateInputStream 输入流（即模板文件）
     * @param excelType           excel类型
     * @param writeSheetData      数据列表
     */
    public static void exportTemplateExcel(OutputStream outputStream,
                                           InputStream templateInputStream,
                                           ExcelTypeEnum excelType,
                                           WriteSheetData... writeSheetData) {
        ExcelWriter writer = EasyExcel.write()
                .withTemplate(templateInputStream)
                .file(outputStream)
                .excelType(excelType)
                .build();
        exportExcel(writer, writeSheetData);
    }

    /**
     * 导出数据到模板excel中
     *
     * @param writer excel writer
     * @param sheet  数据列表
     * @param data   数据列表
     */
    public static void exportExcel(ExcelWriter writer, WriteSheet sheet, List<?> data) {
        writer.write(data, sheet);
        writer.finish();
    }

    /**
     * 导出数据到模板excel中
     *
     * @param writer         excel writer
     * @param writeSheetData 数据列表
     */
    public static void exportExcel(ExcelWriter writer, WriteSheetData... writeSheetData) {
        if (Objects.nonNull(writeSheetData)) {
            for (WriteSheetData sheetData : writeSheetData) {
                writer.write(sheetData.getData(), sheetData.getWriteSheet());
            }
        }
        writer.finish();
    }

    /**
     * 获取数据表
     *
     * @param tClass    数据类型
     * @param sheetNo   数据表编号
     * @param sheetName 数据表 名称
     * @return 数据表
     */
    private static <T> WriteSheet getSheet(Class<T> tClass, Integer sheetNo, String sheetName) {
        boolean needHead = Objects.nonNull(tClass);
        return EasyExcel.writerSheet()
                .needHead(needHead)
                .head(tClass)
                .sheetNo(sheetNo)
                .sheetName(sheetName)
                .build();
    }

}
