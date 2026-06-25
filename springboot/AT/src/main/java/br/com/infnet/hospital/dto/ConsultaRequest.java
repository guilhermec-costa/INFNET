package br.com.infnet.hospital.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConsultaRequest(
        @NotNull LocalDateTime dataConsulta,
        String observacoes,
        @NotNull Long pacienteId,
        @NotNull Long medicoId
) {
}
