package com.bitprogress.validation.validator;

import com.bitprogress.validation.annotation.IsAllowedText;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;

/**
 * 文本校验器
 */
public class IsAllowedTextCollectionValidator extends AbstractIsAllowedTextValidator
        implements ConstraintValidator<IsAllowedText, Collection<String>>, CollectionItemValidator {

    @Override
    public void initialize(IsAllowedText constraintAnnotation) {
        super.setRequired(constraintAnnotation.isRequired());
        super.setIgnoreSpaces(constraintAnnotation.ignoreSpaces());
        super.setTextTypes(constraintAnnotation.textTypes());
        super.setRegex(constraintAnnotation.regex());
    }

    @Override
    public boolean isValid(Collection<String> allowedTexts, ConstraintValidatorContext constraintValidatorContext) {
        return validCollection(super.isRequired(), allowedTexts, super::valid);
    }

}
