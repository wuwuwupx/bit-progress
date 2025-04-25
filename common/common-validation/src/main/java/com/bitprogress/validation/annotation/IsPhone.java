package com.bitprogress.validation.annotation;

import com.bitprogress.validation.validator.IsPhoneCollectionValidator;
import com.bitprogress.validation.validator.IsPhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 电话号码校验注解
 * 支持手机号码和固定电话号码
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsPhoneValidator.class, IsPhoneCollectionValidator.class})
public @interface IsPhone {

    String message() default "电话号码格式有误";

    boolean isRequired() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}