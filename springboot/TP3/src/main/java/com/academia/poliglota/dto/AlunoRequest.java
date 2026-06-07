package com.academia.poliglota.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record AlunoRequest(
        @NotBlank String nome,
        @Email @NotBlank String email,
        @NotNull LocalDate dataNascimento,
        @NotNull Boolean ativo,
        @NotNull Long planoId
) {
}
