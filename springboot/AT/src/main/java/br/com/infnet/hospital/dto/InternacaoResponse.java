package br.com.infnet.hospital.dto;

import java.time.LocalDate;

public record InternacaoResponse(
        Long id,
        LocalDate dataEntrada,
        LocalDate dataAlta,
        String quarto,
        Long pacienteId
) {
}
