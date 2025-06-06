package com.bitprogress.validation.validator;

import com.bitprogress.validation.annotation.IsMobile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 手机号码校验器
 */
public class IsMobileValidator extends AbstractIsMobileValidator implements ConstraintValidator<IsMobile, String> {

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        super.setRequired(constraintAnnotation.isRequired());
    }

    @Override
    public boolean isValid(String src, ConstraintValidatorContext constraintValidatorContext) {
        return super.valid(src);
    }

}
