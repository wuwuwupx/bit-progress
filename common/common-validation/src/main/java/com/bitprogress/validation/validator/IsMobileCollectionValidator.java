package com.bitprogress.validation.validator;

import com.bitprogress.validation.annotation.IsMobile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;

/**
 * 手机号码校验器
 */
public class IsMobileCollectionValidator extends AbstractIsMobileValidator
        implements ConstraintValidator<IsMobile, Collection<String>>, CollectionItemValidator {

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        super.setRequired(constraintAnnotation.isRequired());
    }

    @Override
    public boolean isValid(Collection<String> mobiles, ConstraintValidatorContext constraintValidatorContext) {
        return validCollection(super.isRequired(), mobiles, super::valid);
    }

}
