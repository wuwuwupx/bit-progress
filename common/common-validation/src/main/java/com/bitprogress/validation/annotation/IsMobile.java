package com.bitprogress.validation.annotation;

import com.bitprogress.validation.validator.IsMobileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author wuwuwupx
 * 手机号码校验注解
 * 仅支持校验中国大陆的手机号码
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsMobileValidator.class})
public @interface IsMobile {

    String message() default "手机号码格式有误";

    boolean isRequired() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}