package com.academia.poliglota.config;

import com.academia.poliglota.model.Aluno;
import com.academia.poliglota.model.AlunoTreino;
import com.academia.poliglota.model.Instrutor;
import com.academia.poliglota.model.Plano;
import com.academia.poliglota.model.Treino;
import com.academia.poliglota.repository.AlunoRepository;
import com.academia.poliglota.repository.AlunoTreinoRepository;
import com.academia.poliglota.repository.InstrutorRepository;
import com.academia.poliglota.repository.PlanoRepository;
import com.academia.poliglota.repository.TreinoRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner carregarDadosIniciais(
            PlanoRepository planoRepository,
            InstrutorRepository instrutorRepository,
            AlunoRepository alunoRepository,
            TreinoRepository treinoRepository,
            AlunoTreinoRepository alunoTreinoRepository) {
        return args -> {
            if (planoRepository.count() > 0 || instrutorRepository.count() > 0) {
                return;
            }

            Plano basico = new Plano();
            basico.setNomePlano("Básico");
            basico.setValor(new BigDecimal("99.90"));

            Plano premium = new Plano();
            premium.setNomePlano("Premium");
            premium.setValor(new BigDecimal("159.90"));

            planoRepository.saveAll(List.of(basico, premium));

            Instrutor instrutor1 = new Instrutor();
            instrutor1.setNome("Carla Mendes");
            instrutor1.setCref("CREF12345");

            Instrutor instrutor2 = new Instrutor();
            instrutor2.setNome("Rafael Costa");
            instrutor2.setCref("CREF67890");

            instrutorRepository.saveAll(List.of(instrutor1, instrutor2));

            Aluno aluno1 = new Aluno();
            aluno1.setNome("Ana Silva");
            aluno1.setEmail("ana.silva@academia.com");
            aluno1.setDataNascimento(LocalDate.of(1998, 4, 12));
            aluno1.setAtivo(true);
            aluno1.setPlano(basico);

            Aluno aluno2 = new Aluno();
            aluno2.setNome("Bruno Lima");
            aluno2.setEmail("bruno.lima@academia.com");
            aluno2.setDataNascimento(LocalDate.of(1995, 8, 3));
            aluno2.setAtivo(true);
            aluno2.setPlano(premium);

            alunoRepository.saveAll(List.of(aluno1, aluno2));

            Treino treino1 = new Treino();
            treino1.setNomeTreino("Treino A");
            treino1.setFocoPrincipal("Hipertrofia");
            treino1.setInstrutor(instrutor1);

            Treino treino2 = new Treino();
            treino2.setNomeTreino("Treino B");
            treino2.setFocoPrincipal("Cardio");
            treino2.setInstrutor(instrutor2);

            treinoRepository.saveAll(List.of(treino1, treino2));

            AlunoTreino alunoTreino1 = new AlunoTreino();
            alunoTreino1.setAluno(aluno1);
            alunoTreino1.setTreino(treino1);
            alunoTreino1.setDataInicio(LocalDate.now().minusDays(20));
            alunoTreino1.setStatusConclusao(true);

            AlunoTreino alunoTreino2 = new AlunoTreino();
            alunoTreino2.setAluno(aluno1);
            alunoTreino2.setTreino(treino2);
            alunoTreino2.setDataInicio(LocalDate.now().minusDays(10));
            alunoTreino2.setStatusConclusao(true);

            AlunoTreino alunoTreino3 = new AlunoTreino();
            alunoTreino3.setAluno(aluno2);
            alunoTreino3.setTreino(treino2);
            alunoTreino3.setDataInicio(LocalDate.now().minusDays(7));
            alunoTreino3.setStatusConclusao(false);

            alunoTreinoRepository.saveAll(List.of(alunoTreino1, alunoTreino2, alunoTreino3));
        };
    }
}
