package com.bitprogress.validation.validator;

import com.bitprogress.validation.annotation.IsIdCard;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 身份证校验器
 */
public class IsIdCardValidator extends AbstractIsIdCardValidator implements ConstraintValidator<IsIdCard, String> {

    @Override
    public void initialize(IsIdCard constraintAnnotation) {
        super.setRequired(constraintAnnotation.isRequired());
    }

    @Override
    public boolean isValid(String src, ConstraintValidatorContext constraintValidatorContext) {
        return super.valid(src);
    }

}
