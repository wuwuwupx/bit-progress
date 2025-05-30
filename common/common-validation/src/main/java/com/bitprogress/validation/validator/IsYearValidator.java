package com.bitprogress.validation.validator;

import com.bitprogress.util.StringUtils;
import com.bitprogress.validation.annotation.IsYear;
import com.bitprogress.validation.utils.ValidatorUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 年份校验器
 */
public class IsYearValidator extends AbstractIsYearValidator implements ConstraintValidator<IsYear, String> {

    @Override
    public void initialize(IsYear constraintAnnotation) {
        super.setRequired(constraintAnnotation.isRequired());
    }

    @Override
    public boolean isValid(String src, ConstraintValidatorContext constraintValidatorContext) {
        return valid(src);
    }
}
