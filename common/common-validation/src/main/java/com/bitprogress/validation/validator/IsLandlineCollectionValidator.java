package com.bitprogress.validation.validator;

import com.bitprogress.validation.annotation.IsLandline;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;

/**
 * 固定电话号码校验器
 */
public class IsLandlineCollectionValidator extends AbstractIsLandlineValidator
        implements ConstraintValidator<IsLandline, Collection<String>>, CollectionItemValidator {

    @Override
    public void initialize(IsLandline constraintAnnotation) {
        super.setRequired(constraintAnnotation.isRequired());
    }

    @Override
    public boolean isValid(Collection<String> landlines, ConstraintValidatorContext constraintValidatorContext) {
        return validCollection(super.isRequired(), landlines, super::valid);
    }

}
