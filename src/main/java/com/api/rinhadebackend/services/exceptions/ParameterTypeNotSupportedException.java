package com.api.rinhadebackend.services.exceptions;

public class ParameterTypeNotSupportedException extends RuntimeException {
  public ParameterTypeNotSupportedException(String message) {
    super(message);
  }
}
