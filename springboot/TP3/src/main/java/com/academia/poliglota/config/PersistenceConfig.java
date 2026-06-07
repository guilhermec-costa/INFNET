package com.academia.poliglota.config;

import com.academia.poliglota.model.Aluno;
import com.academia.poliglota.repository.AlunoRepository;
import com.academia.poliglota.repository.AvaliacaoFisicaRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = AlunoRepository.class)
@EnableMongoRepositories(basePackageClasses = AvaliacaoFisicaRepository.class)
@EntityScan(basePackageClasses = Aluno.class)
public class PersistenceConfig {
}
