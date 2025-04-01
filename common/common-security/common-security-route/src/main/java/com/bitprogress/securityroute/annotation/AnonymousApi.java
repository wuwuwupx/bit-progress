package com.bitprogress.securityroute.annotation;

import java.lang.annotation.*;

/**
 * 匿名接口
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AnonymousApi {
}
