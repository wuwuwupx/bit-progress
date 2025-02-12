package com.bitprogress.validation.validator;

import com.bitprogress.util.StringUtils;
import com.bitprogress.validation.annotation.IsChineseName;
import com.bitprogress.validation.utils.ValidatorUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author wuwuwupx
 * 中文姓名校验器
 */
public class IsChineseNameValidator implements ConstraintValidator<IsChineseName, String> {

    private boolean required = false;
    private boolean ignoreSpaces = false;

    @Override
    public void initialize(IsChineseName constraintAnnotation) {
        required = constraintAnnotation.isRequired();
        ignoreSpaces = constraintAnnotation.ignoreSpaces();
    }

    @Override
    public boolean isValid(String src, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return ValidatorUtils.isChineseName(src, ignoreSpaces);
        } else {
            if (StringUtils.isEmpty(src)) {
                return true;
            } else {
                return ValidatorUtils.isChineseName(src, ignoreSpaces);
            }
        }
    }
}
