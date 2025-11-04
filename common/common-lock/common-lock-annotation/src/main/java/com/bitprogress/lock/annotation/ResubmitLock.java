package com.bitprogress.lock.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ResubmitLock {

    /**
     * 锁的参数
     */
    LockParam[] lockParams() default {};

    /**
     * 锁的key的前缀
     */
    String keyPrefix() default "";

    /**
     * 锁的过期时间
     */
    long expire() default 60;

    /**
     * 锁的过期时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 是否释放
     */
    boolean release() default true;

}
