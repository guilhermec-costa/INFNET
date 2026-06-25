package br.com.infnet.hospital.dto;

import java.time.LocalDate;

public record PacienteResponse(
        Long id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String telefone
) {
}
