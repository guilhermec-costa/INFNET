package br.com.infnet.hospital.dto;

public record MedicoConsultaResumoResponse(
        Long medicoId,
        String nome,
        String especialidade,
        long quantidadeConsultas
) {
}
