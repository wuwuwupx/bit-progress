package com.bitprogress.validation.validator;

import com.bitprogress.util.StringUtils;
import com.bitprogress.validation.utils.ValidatorUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 电话号码校验器
 */
@Getter
@Setter
public abstract class AbstractIsPhoneValidator {

    private boolean required = false;

    public boolean valid(String src) {
        return required ? ValidatorUtils.isPhone(src) : StringUtils.isEmpty(src) || ValidatorUtils.isPhone(src);
    }

}
