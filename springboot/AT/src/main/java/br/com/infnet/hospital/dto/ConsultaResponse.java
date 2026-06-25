package br.com.infnet.hospital.dto;

import java.time.LocalDateTime;

public record ConsultaResponse(
        Long id,
        LocalDateTime dataConsulta,
        String observacoes,
        Long pacienteId,
        String pacienteNome,
        Long medicoId,
        String medicoNome
) {
}
