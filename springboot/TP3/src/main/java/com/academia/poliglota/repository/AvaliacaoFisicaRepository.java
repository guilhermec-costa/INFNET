package com.academia.poliglota.repository;

import com.academia.poliglota.model.AvaliacaoFisica;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AvaliacaoFisicaRepository extends MongoRepository<AvaliacaoFisica, String> {

    List<AvaliacaoFisica> findByAlunoId(Long alunoId);
}
