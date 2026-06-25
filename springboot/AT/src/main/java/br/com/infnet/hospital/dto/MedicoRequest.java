package br.com.infnet.hospital.dto;

import jakarta.validation.constraints.NotBlank;

public record MedicoRequest(
        @NotBlank String nome,
        @NotBlank String crm,
        @NotBlank String especialidade
) {
}
