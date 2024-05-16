package com.bitprogress.excelcore.listener;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.bitprogress.excelmodel.annotation.ExcelHeaderCheck;
import com.bitprogress.exception.CommonException;
import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 处理表头
 *
 * @param <T> 数据类型
 */
@Getter
public abstract class ExcelEventListener<T> extends AnalysisEventListener<T> {

    /**
     * Returns the header as a map.Override the current method to receive header data.
     *
     * @param headMap 表头信息存储 map
     * @param context 上下文
     */
    @SneakyThrows
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        Collection<String> values = headMap.values();
        Class<T> tClass = getTClass();
        // 获取类上的注解
        ExcelHeaderCheck classHeader = tClass.getAnnotation(ExcelHeaderCheck.class);
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            // ExcelHeaderCheck 为空则不检查列明
            ExcelHeaderCheck fieldHeader = field.getAnnotation(ExcelHeaderCheck.class);
            ExcelHeaderCheck finalHeader = Objects.isNull(fieldHeader) ? classHeader : fieldHeader;
            if (Objects.isNull(finalHeader)) {
                return;
            }
            // 获取列名
            ExcelProperty property = field.getAnnotation(ExcelProperty.class);
            String headerName = Objects.isNull(property) ? field.getName() : property.value()[0];
            boolean check = finalHeader.check();
            // 不需要检查列名
            if (!check) {
                return;
            }
            // 是否需要检查 index
            boolean checkIndex = finalHeader.checkIndex();
            // 从 ExcelProperty 中获取 index
            int index = checkIndex ? finalHeader.index() : -1;
            // 不需要根据索引检查
            if (index < 0) {
                if (!values.contains(headerName)) {
                    throw CommonException.error("列：" + headerName + " 不存在");
                }
            } else {
                // 需要根据索引检查
                String headValue = headMap.get(index);
                if (!headerName.equals(headValue)) {
                    throw CommonException.error("第" + (index + 1) + "列的列名与 " + headerName + " 不匹配");
                }
            }
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

}
