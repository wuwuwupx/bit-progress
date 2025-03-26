package com.bitprogress.ormparser.annotation;

import com.bitprogress.ormmodel.enums.DataScopeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据权限注解中最高优先级
 * 标记数据权限为当前数据权限 {@link DataScopeType#SELF}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentDataScope {
}
