package com.example.biblioteca.repository;

import com.example.biblioteca.model.Emprestimo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByAtivoTrue();

    boolean existsByLeitorIdAndAtivoTrue(Long leitorId);
}
