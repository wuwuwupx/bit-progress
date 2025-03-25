package com.bitprogress.ormmodel.annotation;

import java.lang.annotation.*;

/**
 * 标记租户类型为操作租户
 * 优先级高于 {@link MarkTenantType}
 * 优先级低于 {@link CurrentTenant}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateTenant {
}
