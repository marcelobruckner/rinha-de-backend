package com.api.rinhadebackend.dtos.mapper;

import org.springframework.stereotype.Component;

import com.api.rinhadebackend.dtos.pessoa.PessoaDto;
import com.api.rinhadebackend.models.Pessoa;

@Component
public class PessoaMapper {

  public PessoaDto toDto(Pessoa pessoa) {
    if (pessoa == null) {
      return null;
    }

    return new PessoaDto(pessoa.getId(), pessoa.getApelido(),
        pessoa.getNome(), pessoa.getNascimento(), pessoa.getStack());
  }

  public Pessoa toEntity(PessoaDto dto) {
    if (dto == null) {
      return null;
    }

    var pessoa = new Pessoa();
    pessoa.setId(dto.id());
    pessoa.setApelido(dto.apelido());
    pessoa.setNome(dto.nome());
    pessoa.setNascimento(dto.nascimento());
    pessoa.setStack(dto.stack());

    return pessoa;
  }

}
