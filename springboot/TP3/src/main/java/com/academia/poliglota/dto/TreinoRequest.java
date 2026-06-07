package com.academia.poliglota.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TreinoRequest(
        @NotBlank String nomeTreino,
        @NotBlank String focoPrincipal,
        @NotNull Long instrutorId
) {
}
