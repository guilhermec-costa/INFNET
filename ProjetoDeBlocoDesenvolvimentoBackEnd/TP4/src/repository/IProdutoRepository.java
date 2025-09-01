package src.repository;

import java.util.List;
import java.util.Optional;

import src.model.Produto;

public interface IProdutoRepository {
    List<Produto> listarTodos();
    Optional<Produto> buscarPorId(long id);  
}
