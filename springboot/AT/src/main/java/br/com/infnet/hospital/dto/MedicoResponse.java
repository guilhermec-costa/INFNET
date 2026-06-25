package br.com.infnet.hospital.dto;

public record MedicoResponse(
        Long id,
        String nome,
        String crm,
        String especialidade
) {
}
