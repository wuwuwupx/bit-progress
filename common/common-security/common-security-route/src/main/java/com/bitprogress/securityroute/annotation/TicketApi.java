package com.bitprogress.securityroute.annotation;

import java.lang.annotation.*;

/**
 * ticket 接口
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TicketApi {
}
