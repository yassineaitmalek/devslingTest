package com.devsling.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.devsling.validation.impl.CarDTOValidatorImpl;

@Documented
@Constraint(validatedBy = { CarDTOValidatorImpl.class })
@Target({ ElementType.TYPE, ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CarDTOValidator {

    String message() default "CarDTO not valid.";

    boolean required() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] values() default {};
}