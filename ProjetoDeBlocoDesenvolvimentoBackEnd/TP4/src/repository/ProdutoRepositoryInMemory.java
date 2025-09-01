package src.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import src.model.Produto;

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