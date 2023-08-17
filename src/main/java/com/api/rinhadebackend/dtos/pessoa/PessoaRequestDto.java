package com.api.rinhadebackend.dtos.pessoa;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.api.rinhadebackend.dtos.pessoa.validators.ValidArrayStringLength;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PessoaRequestDto(
    @NotNull(message = "{apelido.notNull}") @Length(min = 1, max = 32, message = "{apelido.length}") String apelido,
    @NotNull(message = "{nome.notNull}") @Length(min = 1, max = 100, message = "{nome.length}") String nome,
    @NotNull(message = "{nascimento.notNull}") @Pattern(regexp = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "{nascimento.pattern}") String nascimento,
    @ValidArrayStringLength List<String> stack) {
}