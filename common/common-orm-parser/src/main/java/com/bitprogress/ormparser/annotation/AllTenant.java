package com.bitprogress.ormparser.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 全部租户
 * <p>
 * 优先级高于 {@link MarkTenantType}
 * 优先级低于 {@link CurrentTenant} 和 {@link OperateTenant}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AllTenant {
}
