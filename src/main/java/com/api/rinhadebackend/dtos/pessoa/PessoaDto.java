package com.api.rinhadebackend.dtos.pessoa;

import java.util.List;
import java.util.UUID;

public record PessoaDto(
                UUID id,
                String apelido,
                String nome,
                String nascimento,
                List<String> stack) {

}
