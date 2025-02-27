package com.igsaas.common_core.common.validator;

import com.igsaas.common_core.common.annotations.EnumValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<EnumValid, Enum<?>> {

    private String allowedEnumValues;

    @Override
    public void initialize(EnumValid constraint) {
        Class<? extends Enum<?>> enumClass = constraint.enumClass();
        allowedEnumValues = Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .filter(name -> !name.equals("Unknown"))
                .collect(Collectors.joining(", "));
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) return true;
        final boolean isInvalid = value.name().equals("Unknown");
        if (isInvalid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Value must be one of : " + allowedEnumValues
            ).addConstraintViolation();
            return false;
        }
        return true;
    }
}
