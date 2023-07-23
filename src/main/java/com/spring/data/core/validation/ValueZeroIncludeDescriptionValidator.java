package com.spring.data.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;

import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

public class ValueZeroIncludeDescriptionValidator implements ConstraintValidator<ValueZeroIncludeDescription, Object> {
    private String valueField;
    private String descriptionField;
    private String descriptionRequired;

    @Override
    public void initialize(ValueZeroIncludeDescription constraintAnnotation) {
        this.valueField = constraintAnnotation.valueField();
        this.descriptionField = constraintAnnotation.descriptionField();
        this.descriptionRequired = constraintAnnotation.descriptionRequired();
    }

    @Override
    public boolean isValid(Object objectValidation, ConstraintValidatorContext context) {
        boolean valid = true;

        try {
            BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(objectValidation.getClass(), valueField)
                    .getReadMethod().invoke(objectValidation);

            String description = (String) BeanUtils.getPropertyDescriptor(objectValidation.getClass(), descriptionField)
                    .getReadMethod().invoke(objectValidation);

            if (value != null && BigDecimal.ZERO.compareTo(value) == 0 && description != null) {
                valid = description.toLowerCase().contains(this.descriptionRequired.toLowerCase());
            }

            return valid;
        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }
}
