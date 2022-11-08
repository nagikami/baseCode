package com.nagi.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义validator验证约束
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Integer> {

    protected int value;

    @Override
    public void initialize(MyConstraint constraintAnnotation) {
        value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value > this.value;
    }
}
