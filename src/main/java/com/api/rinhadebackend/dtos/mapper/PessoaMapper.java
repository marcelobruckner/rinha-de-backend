package com.api.rinhadebackend.dtos.mapper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.api.rinhadebackend.dtos.pessoa.PessoaRequestDto;
import com.api.rinhadebackend.dtos.pessoa.PessoaResponseDto;
import com.api.rinhadebackend.models.Pessoa;

@Component
public class PessoaMapper {

  private static final String DATE_FORMAT = "yyyy-MM-dd";

  public PessoaRequestDto toRequestDto(Pessoa pessoa) {
    if (pessoa == null) {
      return null;
    }

    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    return new PessoaRequestDto(pessoa.getApelido(),
        pessoa.getNome(), sdf.format(pessoa.getNascimento()), pessoa.getStack());
  }

  public Pessoa toEntity(PessoaRequestDto dto) {
    if (dto == null) {
      return null;
    }

    var pessoa = new Pessoa();
    pessoa.setApelido(dto.apelido());
    pessoa.setNome(dto.nome());

    LocalDate nascimento = LocalDate.parse(dto.nascimento());

    pessoa.setNascimento(nascimento);
    pessoa.setStack(dto.stack());

    return pessoa;
  }

  public PessoaResponseDto toResponseDto(Pessoa pessoa) {
    if (pessoa == null) {
      return null;
    }

    return new PessoaResponseDto(pessoa.getId(), pessoa.getApelido(),
        pessoa.getNome(), pessoa.getNascimento().format(DateTimeFormatter.ofPattern(DATE_FORMAT)), pessoa.getStack());
  }
}
