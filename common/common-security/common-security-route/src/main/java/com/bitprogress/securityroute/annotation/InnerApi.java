package com.bitprogress.securityroute.annotation;

import java.lang.annotation.*;

/**
 * 内部接口
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerApi {
}
