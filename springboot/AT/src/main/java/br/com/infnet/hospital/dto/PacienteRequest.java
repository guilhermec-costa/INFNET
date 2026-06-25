package br.com.infnet.hospital.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record PacienteRequest(
        @NotBlank String nome,
        @NotBlank @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos") String cpf,
        @NotNull LocalDate dataNascimento,
        @NotBlank String telefone
) {
}
