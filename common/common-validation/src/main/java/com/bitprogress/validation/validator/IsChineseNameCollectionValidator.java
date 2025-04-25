package com.bitprogress.validation.validator;

import com.bitprogress.validation.annotation.IsChineseName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;

/**
 * 中文姓名校验器
 */
public class IsChineseNameCollectionValidator extends AbstractIsChineseNameValidator
        implements ConstraintValidator<IsChineseName, Collection<String>>, CollectionItemValidator {

    @Override
    public void initialize(IsChineseName constraintAnnotation) {
        super.setRequired(constraintAnnotation.isRequired());
        super.setIgnoreSpaces(constraintAnnotation.ignoreSpaces());
    }

    @Override
    public boolean isValid(Collection<String> chineseNames, ConstraintValidatorContext constraintValidatorContext) {
        return validCollection(super.isRequired(), chineseNames, super::valid);
    }

}
