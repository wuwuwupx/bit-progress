package com.bitprogress.excelmodel.annotation;

import java.lang.annotation.*;

/**
 * 合并单元格
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelMergeColumn {
}