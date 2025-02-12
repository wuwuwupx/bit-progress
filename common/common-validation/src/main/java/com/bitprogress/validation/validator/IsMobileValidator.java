package com.bitprogress.validation.validator;

import com.bitprogress.util.StringUtils;
import com.bitprogress.validation.annotation.IsMobile;
import com.bitprogress.validation.utils.ValidatorUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author wuwuwupx
 * 手机号码校验器
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.isRequired();
    }

    @Override
    public boolean isValid(String src, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return ValidatorUtils.isMobile(src);
        } else {
            if (StringUtils.isEmpty(src)) {
                return true;
            } else {
                return ValidatorUtils.isMobile(src);
            }
        }
    }

}
