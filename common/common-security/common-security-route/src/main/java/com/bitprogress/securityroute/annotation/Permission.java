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

    String roleKey() default  "";

    String lacksRoleKey() default  "";

    String[] anyRoleKeys() default {};

    String[] allRoleKeys() default {};

}
