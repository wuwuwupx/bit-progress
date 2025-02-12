package com.bitprogress.validation.annotation;

import com.bitprogress.validation.validator.IsIdCardValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author wuwuwupx
 * 身份证校验注解
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsIdCardValidator.class})
public @interface IsIdCard {

    String message() default "身份证号码格式有误";

    boolean isRequired() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}