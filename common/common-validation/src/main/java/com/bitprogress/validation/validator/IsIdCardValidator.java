package com.bitprogress.validation.validator;

import com.bitprogress.util.StringUtils;
import com.bitprogress.validation.annotation.IsIdCard;
import com.bitprogress.validation.utils.ValidatorUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author wuwuwupx
 * 身份证校验器
 */
public class IsIdCardValidator implements ConstraintValidator<IsIdCard, String> {

    private boolean required = false;

    @Override
    public void initialize(IsIdCard constraintAnnotation) {
        required = constraintAnnotation.isRequired();
    }

    @Override
    public boolean isValid(String src, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return ValidatorUtils.isIdCard(src);
        } else {
            if (StringUtils.isEmpty(src)) {
                return true;
            } else {
                return ValidatorUtils.isIdCard(src);
            }
        }
    }
}
