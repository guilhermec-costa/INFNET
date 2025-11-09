package com.sistema.service;

import com.sistema.domain.*;
import com.sistema.exception.ProdutoNaoEncontradoException;
import com.sistema.exception.ProdutoDuplicadoException;
import com.sistema.repository.ProdutoRepositorio;
import java.util.List;

public class ProdutoServico {
    private final ProdutoRepositorio repositorio;

    public ProdutoServico(ProdutoRepositorio repositorio) {
        if (repositorio == null) {
            throw new IllegalArgumentException("Repositório não pode ser nulo");
        }
        this.repositorio = repositorio;
    }

    public Produto criar(NomeProduto nome, Preco preco, Quantidade quantidade, Categoria categoria) {
        Produto produto = Produto.criar(nome, preco, quantidade, categoria);
        
        if (repositorio.existe(produto.getId())) {
            throw new ProdutoDuplicadoException(produto.getId());
        }
        
        repositorio.salvar(produto);
        return produto;
    }

    public Produto buscar(ProdutoId id) {
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    public List<Produto> listarTodos() {
        return repositorio.buscarTodos();
    }

    public Produto atualizar(ProdutoId id, NomeProduto nome, Preco preco, Quantidade quantidade, Categoria categoria) {
        Produto produtoExistente = buscar(id);
        
        Produto produtoAtualizado = Produto.reconstituir(id, nome, preco, quantidade, categoria);
        repositorio.atualizar(produtoAtualizado);
        
        return produtoAtualizado;
    }

    public void deletar(ProdutoId id) {
        if (!repositorio.existe(id)) {
            throw new ProdutoNaoEncontradoException(id);
        }
        
        repositorio.deletar(id);
    }

    public long contarProdutos() {
        return repositorio.contar();
    }

    public boolean existe(ProdutoId id) {
        return repositorio.existe(id);
    }
}