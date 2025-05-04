package com.bitprogress.validation.validator;

import com.bitprogress.validation.annotation.IsAllowedText;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Getter;
import lombok.Setter;

/**
 * 文本类型校验器
 */
@Getter
@Setter
public class IsAllowedTextValidator  extends AbstractIsAllowedTextValidator
        implements ConstraintValidator<IsAllowedText, String> {

    @Override
    public void initialize(IsAllowedText constraintAnnotation) {
        super.setRequired(constraintAnnotation.isRequired());
        super.setIgnoreSpaces(constraintAnnotation.ignoreSpaces());
        super.setTextTypes(constraintAnnotation.textTypes());
        super.setRegex(constraintAnnotation.regex());
    }

    @Override
    public boolean isValid(String src, ConstraintValidatorContext constraintValidatorContext) {
        return valid(src);
    }

}
