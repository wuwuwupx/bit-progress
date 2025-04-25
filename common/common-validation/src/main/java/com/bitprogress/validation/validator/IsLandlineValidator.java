package com.bitprogress.validation.validator;

import com.bitprogress.validation.annotation.IsLandline;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 固定电话号码校验器
 */
public class IsLandlineValidator extends AbstractIsLandlineValidator implements ConstraintValidator<IsLandline, String> {

    @Override
    public void initialize(IsLandline constraintAnnotation) {
        super.setRequired(constraintAnnotation.isRequired());
    }

    @Override
    public boolean isValid(String src, ConstraintValidatorContext constraintValidatorContext) {
        return valid(src);
    }

}
