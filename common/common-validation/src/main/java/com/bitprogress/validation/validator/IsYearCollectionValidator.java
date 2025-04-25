package com.bitprogress.validation.validator;

import com.bitprogress.validation.annotation.IsYear;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;

/**
 * 年份校验器
 */
public class IsYearCollectionValidator extends AbstractIsYearValidator
        implements ConstraintValidator<IsYear, Collection<String>>, CollectionItemValidator {

    @Override
    public void initialize(IsYear constraintAnnotation) {
        super.setRequired(constraintAnnotation.isRequired());
    }

    @Override
    public boolean isValid(Collection<String> years, ConstraintValidatorContext constraintValidatorContext) {
        return validCollection(super.isRequired(), years, super::valid);
    }
}
