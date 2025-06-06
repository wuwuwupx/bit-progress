package com.bitprogress.validation.validator;

import com.bitprogress.validation.annotation.IsChineseName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 中文姓名校验器
 */
public class IsChineseNameValidator extends AbstractIsChineseNameValidator
        implements ConstraintValidator<IsChineseName, String> {

    @Override
    public void initialize(IsChineseName constraintAnnotation) {
        super.setRequired(constraintAnnotation.isRequired());
        super.setIgnoreSpaces(constraintAnnotation.ignoreSpaces());
    }

    @Override
    public boolean isValid(String src, ConstraintValidatorContext constraintValidatorContext) {
        return valid(src);
    }

}
