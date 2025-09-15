package com.infnet.TP5.src.repository;

import java.util.List;
import java.util.Optional;

import com.infnet.TP5.src.model.Produto;

public interface IProdutoRepository {
    List<Produto> listarTodos();
    Optional<Produto> buscarPorId(long id);  
}
