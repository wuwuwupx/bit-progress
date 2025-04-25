package com.bitprogress.validation.validator;

import com.bitprogress.validation.annotation.IsIdCard;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;

/**
 * 身份证校验器
 */
public class IsIdCardCollectionValidator extends AbstractIsIdCardValidator
        implements ConstraintValidator<IsIdCard, Collection<String>>, CollectionItemValidator {

    @Override
    public void initialize(IsIdCard constraintAnnotation) {
        super.setRequired(constraintAnnotation.isRequired());
    }

    @Override
    public boolean isValid(Collection<String> idCards, ConstraintValidatorContext constraintValidatorContext) {
        return validCollection(super.isRequired(), idCards, super::valid);
    }
}
