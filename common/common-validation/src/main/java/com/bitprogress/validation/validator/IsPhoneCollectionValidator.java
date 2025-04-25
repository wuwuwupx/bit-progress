package com.bitprogress.validation.validator;

import com.bitprogress.validation.annotation.IsPhone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;

/**
 * 电话号码校验器
 */
public class IsPhoneCollectionValidator extends AbstractIsPhoneValidator
        implements ConstraintValidator<IsPhone, Collection<String>>, CollectionItemValidator {

    @Override
    public void initialize(IsPhone constraintAnnotation) {
        super.setRequired(constraintAnnotation.isRequired());
    }

    @Override
    public boolean isValid(Collection<String> phones, ConstraintValidatorContext constraintValidatorContext) {
        return validCollection(super.isRequired(), phones, super::valid);
    }

}
