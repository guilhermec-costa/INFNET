package com.sistema.exception;

import com.sistema.domain.ProdutoId;

public class ProdutoNaoEncontradoException extends RuntimeException {
    private final ProdutoId produtoId;

    public ProdutoNaoEncontradoException(ProdutoId produtoId) {
        super("Produto não encontrado: " + produtoId.getValor());
        this.produtoId = produtoId;
    }

    public ProdutoId getProdutoId() {
        return produtoId;
    }
}