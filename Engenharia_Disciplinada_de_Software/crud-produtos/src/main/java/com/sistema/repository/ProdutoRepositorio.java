package com.sistema.repository;

import com.sistema.domain.Produto;
import com.sistema.domain.ProdutoId;
import java.util.List;
import java.util.Optional;

public interface ProdutoRepositorio {
    void salvar(Produto produto);
    
    Optional<Produto> buscarPorId(ProdutoId id);
    
    List<Produto> buscarTodos();
    
    void atualizar(Produto produto);
    
    void deletar(ProdutoId id);
    
    boolean existe(ProdutoId id);
    
    long contar();
}