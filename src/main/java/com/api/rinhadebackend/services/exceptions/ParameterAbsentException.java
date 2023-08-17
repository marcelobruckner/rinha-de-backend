package com.api.rinhadebackend.services.exceptions;

public class ParameterAbsentException extends RuntimeException {
  public ParameterAbsentException(String message) {
    super(message);
  }
}
