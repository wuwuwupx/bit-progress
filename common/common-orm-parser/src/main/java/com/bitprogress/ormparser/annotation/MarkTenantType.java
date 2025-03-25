package com.bitprogress.ormparser.annotation;

import com.bitprogress.ormmodel.enums.TenantType;

import java.lang.annotation.*;

/**
 * 标记当前方法需要指定的租户类型
 * 会被以下两个注解覆盖
 * @see CurrentTenant
 * @see OperateTenant
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MarkTenantType {

    TenantType tenantType() default TenantType.CURRENT;

}
