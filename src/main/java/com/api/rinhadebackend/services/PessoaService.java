package com.api.rinhadebackend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.api.rinhadebackend.dtos.mapper.PessoaMapper;
import com.api.rinhadebackend.dtos.pessoa.PessoaRequestDto;
import com.api.rinhadebackend.dtos.pessoa.PessoaResponseDto;
import com.api.rinhadebackend.models.Pessoa;
import com.api.rinhadebackend.repositories.PessoaRepository;
import com.api.rinhadebackend.services.exceptions.NotFoundException;
import com.api.rinhadebackend.services.exceptions.ParameterAbsentException;
import com.api.rinhadebackend.services.exceptions.ParameterTypeNotSupportedException;
import com.api.rinhadebackend.services.exceptions.UnprocessableEntityException;

@Service
public class PessoaService {

  private final PessoaRepository pessoaRepository;
  private final PessoaMapper pessoaMapper;

  public PessoaService(PessoaRepository pessoaRepository, PessoaMapper pessoaMapper) {
    this.pessoaRepository = pessoaRepository;
    this.pessoaMapper = pessoaMapper;
  }

  public Pessoa save(PessoaRequestDto pessoaCreateDto) {

    if (isNumeric(pessoaCreateDto.apelido())) {
      throw new ParameterTypeNotSupportedException("O apelido não pode ser numérico");
    }

    if (isNumeric(pessoaCreateDto.nome())) {
      throw new ParameterTypeNotSupportedException("O nome não pode ser numérico");
    }

    if (pessoaCreateDto.stack() != null) {
      pessoaCreateDto.stack().forEach(stack -> {
        if (isNumeric(stack)) {
          throw new ParameterTypeNotSupportedException("A stack não pode ser numérica");
        }
      });
    }

    try {
      return pessoaRepository.save(pessoaMapper.toEntity(pessoaCreateDto));
    } catch (DataIntegrityViolationException ex) {
      throw new UnprocessableEntityException("Apelido " + pessoaCreateDto.apelido() + " em uso");

    }
  }

  public List<PessoaResponseDto> list() {
    var pessoas = pessoaRepository.findAll();
    return pessoas.stream()
        .map(pessoaMapper::toResponseDto).collect(Collectors.toList());
  }

  public List<PessoaResponseDto> findAllBySearchTerm(String searchTerm) {
    if (searchTerm.isEmpty()) {
      throw new ParameterAbsentException("Algum termo deve ser informado.");
    }
    var pessoas = pessoaRepository.findAllBySearchTerm(searchTerm.toLowerCase());
    return pessoas.stream()
        .map(pessoaMapper::toResponseDto).collect(Collectors.toList());
  }

  public Long countPeople() {
    return pessoaRepository.count();
  }

  public PessoaResponseDto findById(UUID pessoaId) {
    Optional<Pessoa> pessoaOpt = pessoaRepository.findById(pessoaId);

    return pessoaMapper.toResponseDto(pessoaOpt.orElseThrow(() -> new NotFoundException(pessoaId.toString())));
  }

  private boolean isNumeric(String str) {
    return str != null && str.matches("[0-9.]+");
  }

  public void deleteAll() {
    pessoaRepository.deleteAll();
  }

}
