package com.bitprogress.validation.annotation;

import com.bitprogress.validation.validator.IsChineseNameCollectionValidator;
import com.bitprogress.validation.validator.IsChineseNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author wuwuwupx
 * 中文姓名校验注解
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsChineseNameValidator.class, IsChineseNameCollectionValidator.class})
public @interface IsChineseName {

    String message() default "中文姓名只允许输入汉字和·";

    boolean ignoreSpaces() default false;

    boolean isRequired() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}