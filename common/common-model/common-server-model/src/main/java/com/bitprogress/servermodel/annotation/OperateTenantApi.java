package com.bitprogress.servermodel.annotation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

import java.lang.annotation.*;

import static com.bitprogress.servermodel.constant.TenantConstant.OPERATE_TENANT_KEY;
import static com.bitprogress.servermodel.constant.TenantConstant.OPERATE_TENANT_KEY_NAME;

/**
 * 在请求头设置操作租户ID
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Parameter(name = OPERATE_TENANT_KEY, description = OPERATE_TENANT_KEY_NAME, in = ParameterIn.HEADER)
public @interface OperateTenantApi {

    /**
     * 是否必须
     */
    boolean required() default true;

}
