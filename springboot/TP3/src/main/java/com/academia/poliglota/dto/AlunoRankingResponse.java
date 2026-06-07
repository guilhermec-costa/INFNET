package com.academia.poliglota.dto;

public record AlunoRankingResponse(
        Long alunoId,
        String nomeAluno,
        Long totalTreinosConcluidos
) {
}
