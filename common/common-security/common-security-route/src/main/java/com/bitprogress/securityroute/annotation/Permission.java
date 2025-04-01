package com.bitprogress.securityroute.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {

    String permission() default "";

    String lacksPermission() default "";

    String[] anyPermission() default {};

    String[] allPermission() default {};

    long roleId() default  -1;

    long lacksRoleId() default  -1;

    long[] anyRoleIds() default {};

    long[] allRoleIds() default {};

}
