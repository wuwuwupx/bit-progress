package com.bitprogress.lock.annotation;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LockParam {

    /**
     * 参数表达式
     */
    String expression() default "";

    /**
     * 参数类型
     */
    LockParamType paramType() default LockParamType.SPRING_EL;

}
