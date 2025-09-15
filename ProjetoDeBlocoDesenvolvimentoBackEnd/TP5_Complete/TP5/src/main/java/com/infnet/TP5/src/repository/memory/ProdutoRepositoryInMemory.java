package com.infnet.TP5.src.repository.memory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.infnet.TP5.src.model.Produto;
import com.infnet.TP5.src.repository.IProdutoRepository;

public class ProdutoRepositoryInMemory implements IProdutoRepository {
    private List<Produto> produtos = new ArrayList<>();

    public ProdutoRepositoryInMemory() {
        produtos.add(new Produto(1, "Notebook Gamer", "Notebook de última geração", new BigDecimal("7500.00"), 10));
        produtos.add(new Produto(2, "Mouse sem fio", "Mouse ergonômico", new BigDecimal("150.00"), 30));
        produtos.add(new Produto(3, "Teclado Mecânico", "Teclado com switches blue", new BigDecimal("450.00"), 15));
    }

    public List<Produto> listarTodos() {
        return produtos;
    }

    public Optional<Produto> buscarPorId(long id) {
        return produtos.stream().filter(p -> p.getId() == id).findFirst();
    }
}