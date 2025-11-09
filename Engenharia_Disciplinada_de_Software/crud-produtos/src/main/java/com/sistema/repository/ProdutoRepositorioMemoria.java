package com.sistema.repository;

import com.sistema.domain.Produto;
import com.sistema.domain.ProdutoId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ProdutoRepositorioMemoria implements ProdutoRepositorio {
    private final Map<ProdutoId, Produto> produtos;

    public ProdutoRepositorioMemoria() {
        this.produtos = new ConcurrentHashMap<>();
    }

    @Override
    public void salvar(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }
        produtos.put(produto.getId(), produto);
    }

    @Override
    public Optional<Produto> buscarPorId(ProdutoId id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return Optional.ofNullable(produtos.get(id));
    }

    @Override
    public List<Produto> buscarTodos() {
        return new ArrayList<>(produtos.values());
    }

    @Override
    public void atualizar(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }
        if (!produtos.containsKey(produto.getId())) {
            throw new IllegalStateException("Produto não existe para ser atualizado");
        }
        produtos.put(produto.getId(), produto);
    }

    @Override
    public void deletar(ProdutoId id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        produtos.remove(id);
    }

    @Override
    public boolean existe(ProdutoId id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return produtos.containsKey(id);
    }

    @Override
    public long contar() {
        return produtos.size();
    }
}