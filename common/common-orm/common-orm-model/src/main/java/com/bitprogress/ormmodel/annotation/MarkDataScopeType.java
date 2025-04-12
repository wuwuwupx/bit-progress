package com.bitprogress.ormmodel.annotation;

import com.bitprogress.ormmodel.enums.DataScopeType;

import java.lang.annotation.*;

/**
 * 标记数据权限类型
 * 优先级最低
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MarkDataScopeType {

    /**
     * 数据权限类型
     * 默认为自身
     */
    DataScopeType dataScopeType() default DataScopeType.SELF;

}
