package com.api.rinhadebackend.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "pessoas")
public class Pessoa implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  @Column(nullable = false, unique = true, length = 32)
  private String apelido;
  @Column(nullable = false, length = 100)
  private String nome;
  @Column(nullable = false)
  private LocalDate nascimento;
  @ElementCollection
  @CollectionTable(name = "pessoas_stacks", joinColumns = @JoinColumn(name = "pessoa_id"))
  @Column(nullable = true, length = 32)
  private List<String> stack;

  public Pessoa() {

  }

  public Pessoa(UUID id, String apelido, String nome, LocalDate nascimento) {
    this.id = id;
    this.apelido = apelido;
    this.nome = nome;
    this.nascimento = nascimento;
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

  public LocalDate getNascimento() {
    return nascimento;
  }

  public void setNascimento(LocalDate nascimento) {
    this.nascimento = nascimento;
  }

  public List<String> getStack() {
    return stack;
  }

  public void setStack(List<String> stack) {
    this.stack = stack;
  }

  @Override
  public String toString() {
    return "Pessoa [id=" + id + ", apelido=" + apelido + ", nome=" + nome + ", nascimento=" + nascimento + "]";
  }

}
