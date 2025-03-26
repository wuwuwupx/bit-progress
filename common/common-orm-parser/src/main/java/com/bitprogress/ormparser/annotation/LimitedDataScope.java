package com.bitprogress.ormparser.annotation;

import com.bitprogress.ormmodel.enums.DataScopeType;

import java.lang.annotation.*;

/**
 * 标记 数据权限 为 {@link DataScopeType#LIMITED}
 * 数据权限注解
 * 优先级高于 {@link AllDataScope} 和 {@link MarkDataScopeType}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LimitedDataScope {
}
