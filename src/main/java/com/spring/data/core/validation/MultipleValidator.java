package com.spring.data.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class MultipleValidator implements ConstraintValidator<Multiple, Number> {

    private int numberMultiple;

    @Override
    public void initialize(Multiple constraintAnnotation) {
        this.numberMultiple = constraintAnnotation.number();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        boolean valid = true;

        if (value != null) {
            var decimalValue = BigDecimal.valueOf(value.doubleValue());
            var multipleDecimal = BigDecimal.valueOf(this.numberMultiple);
            var rest = decimalValue.remainder(multipleDecimal);

            valid = BigDecimal.ZERO.compareTo(rest) == 0;
        }
        return valid;
    }
}
