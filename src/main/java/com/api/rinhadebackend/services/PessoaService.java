package com.api.rinhadebackend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.rinhadebackend.dtos.mapper.PessoaMapper;
import com.api.rinhadebackend.dtos.pessoa.PessoaDto;
import com.api.rinhadebackend.models.Pessoa;
import com.api.rinhadebackend.repositories.PessoaRepository;
import com.api.rinhadebackend.services.exceptions.NotFoundException;
import com.api.rinhadebackend.services.exceptions.ParameterAbsentException;
import com.api.rinhadebackend.services.exceptions.UnprocessableEntityException;

import jakarta.transaction.Transactional;

@Service
public class PessoaService {

  @Autowired
  private PessoaRepository pessoaRepository;

  @Autowired
  private PessoaMapper pessoaMapper;

  @Transactional()
  public Pessoa save(PessoaDto pessoaDto) {

    var pessoa = pessoaMapper.toEntity(pessoaDto);
    pessoa.setId(UUID.randomUUID());
    try {
      return pessoaRepository.save(pessoa);
    } catch (RuntimeException ex) {
      throw new UnprocessableEntityException(ex.getMessage());

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

}
