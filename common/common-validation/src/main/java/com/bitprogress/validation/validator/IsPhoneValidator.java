package com.bitprogress.validation.validator;

import com.bitprogress.validation.annotation.IsPhone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 电话号码校验器
 */
public class IsPhoneValidator extends AbstractIsPhoneValidator implements ConstraintValidator<IsPhone, String> {

    @Override
    public void initialize(IsPhone constraintAnnotation) {
        super.setRequired(constraintAnnotation.isRequired());
    }

    @Override
    public boolean isValid(String src, ConstraintValidatorContext constraintValidatorContext) {
        return valid(src);
    }

}
