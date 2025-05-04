package com.bitprogress.validation.annotation;

import com.bitprogress.basemodel.enums.text.TextType;
import com.bitprogress.validation.validator.IsAllowedTextCollectionValidator;
import com.bitprogress.validation.validator.IsAllowedTextValidator;
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
@Constraint(validatedBy = {IsAllowedTextValidator.class, IsAllowedTextCollectionValidator.class})
public @interface IsAllowedText {

    String message() default "包含不被允许的文本";

    boolean ignoreSpaces() default false;

    boolean isRequired() default false;

    /**
     * 允许的文本类型
     * 默认为空，为空则不进行校验
     */
    TextType[] textTypes() default {};

    /**
     * 自定义校验正则表达式
     * 默认为空，不为空则使用自定义正则表达式校验，为空则使用textTypes
     */
    String regex() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}