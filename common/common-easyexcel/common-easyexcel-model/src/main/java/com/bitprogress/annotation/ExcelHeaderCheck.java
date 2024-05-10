package com.bitprogress.annotation;

import java.lang.annotation.*;

/**
 * 表头检查，仅适用于简单表头
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelHeaderCheck {

    /**
     * 是否检查列名
     * 默认检查
     */
    boolean check() default true;

    /**
     * 是否检查列名的索引
     * 默认不检查
     */
    boolean checkIndex() default false;

    /**
     * 列索引
     */
    int index() default -1;

}
