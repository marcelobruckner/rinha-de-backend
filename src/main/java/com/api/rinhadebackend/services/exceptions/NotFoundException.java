package com.api.rinhadebackend.services.exceptions;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String pessoaId) {
    super("Pessoa com identificador " + pessoaId + " não encontrada.");
  }
}
