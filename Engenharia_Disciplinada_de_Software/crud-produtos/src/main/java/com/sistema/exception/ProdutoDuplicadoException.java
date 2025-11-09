package com.sistema.exception;

import com.sistema.domain.ProdutoId;

public class ProdutoDuplicadoException extends RuntimeException {
    private final ProdutoId produtoId;

    public ProdutoDuplicadoException(ProdutoId produtoId) {
        super("Produto já existe: " + produtoId.getValor());
        this.produtoId = produtoId;
    }

    public ProdutoId getProdutoId() {
        return produtoId;
    }
}