package com.bitprogress.validation.validator;

import com.bitprogress.util.StringUtils;
import com.bitprogress.validation.utils.ValidatorUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 中文姓名校验器
 */
@Getter
@Setter
public abstract class AbstractIsChineseNameValidator {

    private boolean required = false;
    private boolean ignoreSpaces = false;

    public boolean valid(String src) {
        return required
                ? ValidatorUtils.isChineseName(src, ignoreSpaces)
                : StringUtils.isEmpty(src) || ValidatorUtils.isChineseName(src, ignoreSpaces);
    }
}
