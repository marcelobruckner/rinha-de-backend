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
import com.api.rinhadebackend.services.PessoaService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping("/pessoas")
    public ResponseEntity<PessoaDto> create(@RequestBody @Valid PessoaDto pessoaDto) {
        var pessoa = pessoaService.save(pessoaDto);

        URI uri = UriComponentsBuilder
                .fromPath("/pessoas/{id}")
                .buildAndExpand(pessoa.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/pessoas")
    public ResponseEntity<List<PessoaDto>> findAllBySearchTerm(@RequestParam(value = "t") String searchTerm) {
        return ResponseEntity.ok(pessoaService.findAllBySearchTerm(searchTerm));
    }

    @GetMapping("/pessoas/{id}")
    public ResponseEntity<PessoaDto> findById(@PathVariable(name = "id") UUID pessoaId) {
        return ResponseEntity.ok(pessoaService.findById(pessoaId));
    }

    @GetMapping("/contagem-pessoas")
    @ResponseStatus(code = HttpStatus.OK)
    public Long countPeople() {
        return pessoaService.countPeople();
    }

}
