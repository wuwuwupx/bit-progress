package com.bitprogress.validation.validator;

import com.bitprogress.util.StringUtils;
import com.bitprogress.validation.utils.ValidatorUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 手机号码校验器
 */
@Getter
@Setter
public abstract class AbstractIsMobileValidator {

    private boolean required = false;

    public boolean valid(String src) {
        return required ? ValidatorUtils.isMobile(src) : StringUtils.isEmpty(src) || ValidatorUtils.isMobile(src);
    }

}
