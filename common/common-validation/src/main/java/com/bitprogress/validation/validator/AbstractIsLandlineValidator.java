package com.bitprogress.validation.validator;

import com.bitprogress.util.StringUtils;
import com.bitprogress.validation.annotation.IsLandline;
import com.bitprogress.validation.utils.ValidatorUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Getter;
import lombok.Setter;

/**
 * 固定电话号码校验器
 */
@Getter
@Setter
public abstract class AbstractIsLandlineValidator {

    private boolean required = false;

    public boolean valid(String src) {
        return required ? ValidatorUtils.isLandline(src) : StringUtils.isEmpty(src) || ValidatorUtils.isLandline(src);
    }

}
