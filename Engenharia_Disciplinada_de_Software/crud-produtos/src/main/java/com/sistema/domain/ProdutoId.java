package com.sistema.domain;

import java.util.Objects;
import java.util.UUID;

public final class ProdutoId {
    private final String valor;

    private ProdutoId(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("ID do produto não pode ser nulo ou vazio");
        }
        this.valor = valor;
    }

    public static ProdutoId gerar() {
        return new ProdutoId(UUID.randomUUID().toString());
    }

    public static ProdutoId de(String valor) {
        return new ProdutoId(valor);
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoId produtoId = (ProdutoId) o;
        return Objects.equals(valor, produtoId.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}