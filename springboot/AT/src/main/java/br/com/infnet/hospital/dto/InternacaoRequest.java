package br.com.infnet.hospital.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record InternacaoRequest(
        @NotNull LocalDate dataEntrada,
        LocalDate dataAlta,
        @NotBlank String quarto,
        @NotNull Long pacienteId
) {
}
