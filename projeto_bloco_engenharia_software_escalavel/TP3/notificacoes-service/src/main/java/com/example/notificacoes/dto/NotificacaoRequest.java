package com.example.notificacoes.dto;

import com.example.notificacoes.model.TipoNotificacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificacaoRequest(
        @NotNull Long leitorId,
        @NotBlank String leitorNome,
        @NotNull TipoNotificacao tipo,
        @NotBlank String titulo,
        @NotBlank String mensagem
) {
}
