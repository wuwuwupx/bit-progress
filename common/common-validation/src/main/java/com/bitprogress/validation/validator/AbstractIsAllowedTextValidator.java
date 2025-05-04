package com.bitprogress.validation.validator;

import com.bitprogress.basemodel.enums.text.TextType;
import com.bitprogress.util.StringUtils;
import com.bitprogress.validation.utils.ValidatorUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

/**
 * 文本类型校验器
 */
@Getter
@Setter
public abstract class AbstractIsAllowedTextValidator {

    private boolean required = false;
    private boolean ignoreSpaces = false;
    private TextType[] textTypes = new TextType[]{};
    private String regex = "";

    public boolean valid(String src) {
        if (!required) {
            if (StringUtils.isEmpty(src)) {
                return true;
            }
        }
        if (StringUtils.isEmpty(regex)) {
            return ValidatorUtils.isAllowedText(src, textTypes, ignoreSpaces);
        } else {
            return ValidatorUtils.isLegal(Pattern.compile(Pattern.quote(regex)), src);
        }
    }
}
