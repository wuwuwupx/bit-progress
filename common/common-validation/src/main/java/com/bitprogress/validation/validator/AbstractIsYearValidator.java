package com.bitprogress.validation.validator;

import com.bitprogress.util.StringUtils;
import com.bitprogress.validation.utils.ValidatorUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 年份校验器
 */
@Getter
@Setter
public abstract class AbstractIsYearValidator {

    private boolean required = false;

    public boolean valid(String src) {
        return required ? ValidatorUtils.isYear(src) : StringUtils.isEmpty(src) || ValidatorUtils.isYear(src);
    }
}
