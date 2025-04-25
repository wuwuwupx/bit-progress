package com.bitprogress.validation.validator;

import com.bitprogress.util.StringUtils;
import com.bitprogress.validation.utils.ValidatorUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 身份证校验器
 */
@Setter
@Getter
public abstract class AbstractIsIdCardValidator {

    private boolean required = false;

    /**
     * 校验
     *
     * @param src 需要校验的字符串
     * @return 校验是否通过
     */
    public boolean valid(String src) {
        return required ? ValidatorUtils.isIdCard(src) : StringUtils.isEmpty(src) || ValidatorUtils.isIdCard(src);
    }

}
