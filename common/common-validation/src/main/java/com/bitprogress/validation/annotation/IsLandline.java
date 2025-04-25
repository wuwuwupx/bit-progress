package com.bitprogress.validation.annotation;

import com.bitprogress.validation.validator.IsLandlineCollectionValidator;
import com.bitprogress.validation.validator.IsLandlineValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 是否是固定电话号码
 * 仅支持校验中国大陆的手机号码
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsLandlineValidator.class, IsLandlineCollectionValidator.class})
public @interface IsLandline {

    String message() default "固定电话号码格式有误";

    boolean isRequired() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}