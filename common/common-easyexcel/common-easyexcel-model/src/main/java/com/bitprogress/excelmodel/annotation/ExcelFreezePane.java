package com.bitprogress.excelmodel.annotation;

import java.lang.annotation.*;

/**
 * Excel冻结窗格注解
 * 需要在继承了BaseWriteHandler的处理器中才会生效
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelFreezePane {

    /**
     * 冻结的首列
     */
    int colSplit() default 0;

    /**
     * 冻结的首行
     */
    int rowSplit() default 1;

    /**
     * 冻结后第一列的列号
     */
    int leftmostColumn() default 1;

    /**
     * 冻结后第一行的行号
     */
    int topRow() default 1;

}
