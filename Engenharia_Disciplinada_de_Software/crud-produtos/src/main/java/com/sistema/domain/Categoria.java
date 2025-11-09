package com.sistema.domain;

public enum Categoria {
    ELETRONICOS("Eletrônicos"),
    ALIMENTOS("Alimentos"),
    VESTUARIO("Vestuário"),
    MOVEIS("Móveis"),
    LIVROS("Livros"),
    BRINQUEDOS("Brinquedos"),
    ESPORTES("Esportes"),
    BELEZA("Beleza"),
    AUTOMOTIVO("Automotivo"),
    OUTROS("Outros");

    private final String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Categoria fromDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição da categoria não pode ser nula ou vazia");
        }

        for (Categoria categoria : values()) {
            if (categoria.descricao.equalsIgnoreCase(descricao.trim())) {
                return categoria;
            }
        }

        throw new IllegalArgumentException("Categoria não encontrada: " + descricao);
    }

    @Override
    public String toString() {
        return descricao;
    }
}