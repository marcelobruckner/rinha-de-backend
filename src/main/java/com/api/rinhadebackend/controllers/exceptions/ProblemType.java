package com.api.rinhadebackend.controllers.exceptions;

import lombok.Getter;

@Getter
public enum ProblemType {
    PARAMETRO_INVALIDO("Parâmetro inválido"),
    NOT_FOUND("Entidade não encontrada");

    private String title;

    ProblemType(String title) {
        this.title = title;
    }
}
