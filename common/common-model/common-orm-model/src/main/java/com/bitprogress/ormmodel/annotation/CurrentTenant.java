package com.bitprogress.ormmodel.annotation;

import java.lang.annotation.*;

/**
 * 标记租户类型为当前租户
 * 最高优先级
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentTenant {
}
