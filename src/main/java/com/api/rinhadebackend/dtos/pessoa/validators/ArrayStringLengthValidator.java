package com.api.rinhadebackend.dtos.pessoa.validators;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ArrayStringLengthValidator implements ConstraintValidator<ValidArrayStringLength, List<String>> {

  @Override
  public boolean isValid(List<String> array, ConstraintValidatorContext context) {

    if (array == null) {
      return true;
    }

    for (String str : array) {
      if (str.length() > 32) {
        return false;
      }
    }

    return true;
  }

}
