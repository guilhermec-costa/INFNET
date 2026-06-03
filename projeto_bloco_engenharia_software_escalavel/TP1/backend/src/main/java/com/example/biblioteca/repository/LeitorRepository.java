package com.example.biblioteca.repository;

import com.example.biblioteca.model.Leitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeitorRepository extends JpaRepository<Leitor, Long> {
}
