package com.api.rinhadebackend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.api.rinhadebackend.dtos.mapper.PessoaMapper;
import com.api.rinhadebackend.dtos.pessoa.PessoaDto;
import com.api.rinhadebackend.models.Pessoa;
import com.api.rinhadebackend.repositories.PessoaRepository;
import com.api.rinhadebackend.services.exceptions.NotFoundException;
import com.api.rinhadebackend.services.exceptions.ParameterAbsentException;
import com.api.rinhadebackend.services.exceptions.ParameterTypeNotSupportedException;
import com.api.rinhadebackend.services.exceptions.UnprocessableEntityException;

@Service
public class PessoaService {

  @Autowired
  private PessoaRepository pessoaRepository;

  @Autowired
  private PessoaMapper pessoaMapper;

  public Pessoa save(PessoaDto pessoaDto) {

    if (isNumeric(pessoaDto.apelido())) {
      throw new ParameterTypeNotSupportedException("O apelido não pode ser numérico");
    }

    if (isNumeric(pessoaDto.nome())) {
      throw new ParameterTypeNotSupportedException("O nome não pode ser numérico");
    }

    if (pessoaDto.stack() != null) {
      pessoaDto.stack().forEach(stack -> {
        if (isNumeric(stack)) {
          throw new ParameterTypeNotSupportedException("A stack não pode ser numérica");
        }
      });
    }

    var pessoa = pessoaMapper.toEntity(pessoaDto);
    pessoa.setId(UUID.randomUUID());
    try {
      return pessoaRepository.save(pessoa);
    } catch (DataIntegrityViolationException ex) {
      throw new UnprocessableEntityException("Apelido " + pessoaDto.apelido() + " em uso");

    }
  }

  public List<PessoaDto> findAllBySearchTerm(String searchTerm) {
    if (searchTerm == null || searchTerm.isBlank()) {
      throw new ParameterAbsentException("Algum termo deve ser informado.");
    }
    var pessoas = pessoaRepository.findAllBySearchTerm(searchTerm.toLowerCase());
    return pessoas.stream()
        .map(pessoaMapper::toDto).collect(Collectors.toList());
  }

  public Long countPeople() {
    return pessoaRepository.count();
  }

  public PessoaDto findById(UUID pessoaId) {
    Optional<Pessoa> pessoaOpt = pessoaRepository.findById(pessoaId);

    return pessoaMapper.toDto(pessoaOpt.orElseThrow(() -> new NotFoundException(pessoaId.toString())));
  }

  private boolean isNumeric(String str) {
    return str != null && str.matches("[0-9.]+");
  }

}
