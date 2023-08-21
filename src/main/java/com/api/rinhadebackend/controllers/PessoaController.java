package com.api.rinhadebackend.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.api.rinhadebackend.dtos.pessoa.PessoaDto;
import com.api.rinhadebackend.repositories.PessoaRepository;
import com.api.rinhadebackend.services.PessoaService;
import com.api.rinhadebackend.services.exceptions.ParameterTypeNotSupportedException;
import com.api.rinhadebackend.services.exceptions.UnprocessableEntityException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaRepository repository;

    @GetMapping("/pessoas")
    public ResponseEntity<List<PessoaDto>> findAllBySearchTerm(@RequestParam(value = "t") String searchTerm) {
        return ResponseEntity.ok(pessoaService.findAllBySearchTerm(searchTerm));
    }

    @GetMapping("/pessoas/{id}")
    public ResponseEntity<PessoaDto> findById(@PathVariable(name = "id") UUID pessoaId) {
        return ResponseEntity.ok(pessoaService.findById(pessoaId));
    }

    @PostMapping("/pessoas")
    public ResponseEntity<PessoaDto> create(@RequestBody PessoaDto pessoaDto) {

        validateDto(pessoaDto);

        var pessoa = pessoaService.save(pessoaDto);

        URI uri = UriComponentsBuilder
                .fromPath("/pessoas/{id}")
                .buildAndExpand(pessoa.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/contagem-pessoas")
    @ResponseStatus(code = HttpStatus.OK)
    public Long countPeople() {
        return pessoaService.countPeople();
    }

    private void validateDto(PessoaDto pessoaDto) {

        validateApelido(pessoaDto.apelido());
        validateName(pessoaDto.nome());
        validateStack(pessoaDto.stack());
    }

    private void validateStack(List<String> stacks) {
        if (stacks != null) {
            stacks.forEach(stack -> {
                if (isNumeric(stack)) {
                    // 400
                    throw new ParameterTypeNotSupportedException("A stack não pode ser numérica");
                }
                if (stack.length() > 32) {
                    // 400
                    throw new ParameterTypeNotSupportedException("A stack não pode ter mais de 32 caracteres");
                }
            });
        }
    }

    private void validateName(String nome) {
        if (null == nome
                || nome.isBlank()) {
            // 422
            throw new UnprocessableEntityException("Nome não pode ser nulo ou vazio.");
        }
        if (nome.length() > 100) {
            // 422
            throw new UnprocessableEntityException("Nome não pode ser maior que 100 caracteres.");
        }

        if (isNumeric(nome)) {
            // 400
            throw new ParameterTypeNotSupportedException("O campo nome não deve ser numérico");
        }
    }

    private void validateApelido(String apelido) {
        if (null == apelido
                || apelido.isBlank()) {
            // 422
            throw new UnprocessableEntityException("Apelido não pode ser nulo ou vazio.");
        }

        if (apelido.length() > 32) {
            // 422
            throw new UnprocessableEntityException("Apelido não pode ser maior que 32 caracteres.");
        }

        if (isNumeric(apelido)) {
            // 400
            throw new ParameterTypeNotSupportedException("O campo apelido não deve ser numérico");
        }

        if (repository.findByApelido(apelido) != null) {
            // 422
            throw new UnprocessableEntityException("Apelido " + apelido + " em uso.");
        }
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("[0-9.]+");
    }

}
