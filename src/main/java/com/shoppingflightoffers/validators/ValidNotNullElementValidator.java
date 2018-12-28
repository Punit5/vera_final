package com.shoppingflightoffers.validators;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.CollectionUtils;

final class ValidNotNullElementValidator implements ConstraintValidator<ValidNotNullElement, Object> {
    @Override
    public void initialize(final ValidNotNullElement validNotNullElement) {
        //No implementation necessary
    }
    
    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext context) {
        if (object instanceof Collection<?>) {
            final Collection<?> elements = (Collection<?>) object;
            return !CollectionUtils.isEmpty(elements) && !elements.stream().anyMatch(element -> null == element);
        }
        return false;
    }
}
