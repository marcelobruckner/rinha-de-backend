package com.api.rinhadebackend.dtos.pessoa.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ArrayStringLengthValidator.class)
public @interface ValidArrayStringLength {
  String message() default "Cada stack no array de stacks deve ter no máximo de 32 caracteres.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
