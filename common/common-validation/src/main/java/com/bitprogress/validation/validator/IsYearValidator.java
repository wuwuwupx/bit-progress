package com.bitprogress.validation.validator;

import com.bitprogress.util.StringUtils;
import com.bitprogress.validation.annotation.IsYear;
import com.bitprogress.validation.utils.ValidatorUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author wuwuwupx
 * 年份校验器
 */
public class IsYearValidator implements ConstraintValidator<IsYear, String> {

    private boolean required = false;

    @Override
    public void initialize(IsYear constraintAnnotation) {
        required = constraintAnnotation.isRequired();
    }

    @Override
    public boolean isValid(String src, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return ValidatorUtils.isYear(src);
        } else {
            if (StringUtils.isEmpty(src)) {
                return true;
            } else {
                return ValidatorUtils.isYear(src);
            }
        }
    }
}
