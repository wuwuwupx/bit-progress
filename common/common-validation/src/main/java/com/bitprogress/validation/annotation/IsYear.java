package com.bitprogress.validation.annotation;

import com.bitprogress.validation.validator.IsYearCollectionValidator;
import com.bitprogress.validation.validator.IsYearValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 年份验证注解
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsYearValidator.class, IsYearCollectionValidator.class})
public @interface IsYear {

    String message() default "年份格式有误";

    boolean isRequired() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}