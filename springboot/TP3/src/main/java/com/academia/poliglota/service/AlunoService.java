package com.academia.poliglota.service;

import com.academia.poliglota.dto.AlunoRankingResponse;
import com.academia.poliglota.dto.AlunoRequest;
import com.academia.poliglota.model.Aluno;
import com.academia.poliglota.model.Plano;
import com.academia.poliglota.repository.AlunoRepository;
import com.academia.poliglota.repository.PlanoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final PlanoRepository planoRepository;

    public AlunoService(AlunoRepository alunoRepository, PlanoRepository planoRepository) {
        this.alunoRepository = alunoRepository;
        this.planoRepository = planoRepository;
    }

    public Aluno cadastrar(AlunoRequest request) {
        Plano plano = planoRepository.findById(request.planoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado"));

        Aluno aluno = new Aluno();
        aluno.setNome(request.nome());
        aluno.setEmail(request.email());
        aluno.setDataNascimento(request.dataNascimento());
        aluno.setAtivo(request.ativo());
        aluno.setPlano(plano);

        return alunoRepository.save(aluno);
    }

    public List<Aluno> listarAtivos() {
        return alunoRepository.findByAtivoTrue();
    }

    public List<AlunoRankingResponse> buscarRanking() {
        return alunoRepository.buscarRankingPorConclusoes();
    }
}
