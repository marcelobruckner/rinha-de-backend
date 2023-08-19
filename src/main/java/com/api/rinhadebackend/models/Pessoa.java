package com.api.rinhadebackend.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.api.rinhadebackend.models.converters.StringListConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "pessoas")
public class Pessoa implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  private UUID id;

  @Column(nullable = false, unique = true, length = 32)
  private String apelido;

  @Column(nullable = false, length = 100)
  private String nome;

  @Pattern(regexp = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
  @Column(nullable = false)
  private String nascimento;

  @Column(nullable = true)
  @Convert(converter = StringListConverter.class)
  private List<String> stack;

  public Pessoa() {

  }

  public Pessoa(String apelido, String nome, String nascimento, List<String> stack) {
    this.apelido = apelido;
    this.nome = nome;
    this.nascimento = nascimento;
    this.stack = stack;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getApelido() {
    return apelido;
  }

  public void setApelido(String apelido) {
    this.apelido = apelido;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getNascimento() {
    return nascimento;
  }

  public void setNascimento(String nascimento) {
    this.nascimento = nascimento;
  }

  public List<String> getStack() {
    return stack;
  }

  public void setStack(List<String> stack) {
    this.stack = stack;
  }

}
