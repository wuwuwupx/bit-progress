package com.bitprogress.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CompressionUtils {

    private static final Logger log = LoggerFactory.getLogger(CompressionUtils.class);

    /**
     * 压缩文件或者文件夹
     *
     * @param zipConsumer 压缩函数
     */
    public static ByteArrayOutputStream compress(Consumer<ZipOutputStream> zipConsumer) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            zipConsumer.accept(zipOutputStream);
            zipOutputStream.finish();
            return outputStream;
        } catch (IOException e) {
            log.error("zip error ", e);
            throw new RuntimeException("压缩失败", e);
        }
    }

    /**
     * 压缩文件或者文件夹
     *
     * @param entries 文件列表，key文件名称，value文件内容
     */
    public static ByteArrayOutputStream compressByte(Map<String, byte[]> entries) {
        return compress(zipOutputStream -> {
            if (CollectionUtils.isNotEmpty(entries)) {
                entries.forEach((key, value) -> putZipEntry(zipOutputStream, key, value));
            }
        });
    }

    /**
     * 压缩文件或者文件夹
     *
     * @param entries 文件列表，key文件名称，value文件内容
     */
    public static ByteArrayOutputStream compressByteStream(Map<String, ByteArrayOutputStream> entries) {
        return compress(zipOutputStream -> {
            if (CollectionUtils.isNotEmpty(entries)) {
                entries.forEach((key, value) -> putZipEntry(zipOutputStream, key, value.toByteArray()));
            }
        });
    }

    /**
     * 设置压缩条目
     *
     * @param zipOutputStream 压缩输出流
     * @param fileName        条目文件名
     * @param content         条目内容
     */
    public static void putZipEntry(ZipOutputStream zipOutputStream, String fileName, byte[] content) {
        try {
            zipOutputStream.putNextEntry(new ZipEntry(fileName));
            zipOutputStream.write(content);
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            log.error("zip error key {} ", fileName, e);
            throw new RuntimeException("压缩失败", e);
        }
    }

}
