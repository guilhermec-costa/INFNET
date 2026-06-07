package com.academia.poliglota.service;

import com.academia.poliglota.dto.AvaliacaoFisicaRequest;
import com.academia.poliglota.model.Aluno;
import com.academia.poliglota.model.AvaliacaoFisica;
import com.academia.poliglota.repository.AlunoRepository;
import com.academia.poliglota.repository.AvaliacaoFisicaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AvaliacaoFisicaService {

    private final AvaliacaoFisicaRepository avaliacaoFisicaRepository;
    private final AlunoRepository alunoRepository;

    public AvaliacaoFisicaService(AvaliacaoFisicaRepository avaliacaoFisicaRepository, AlunoRepository alunoRepository) {
        this.avaliacaoFisicaRepository = avaliacaoFisicaRepository;
        this.alunoRepository = alunoRepository;
    }

    public AvaliacaoFisica cadastrar(AvaliacaoFisicaRequest request) {
        Aluno aluno = alunoRepository.findById(request.alunoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        AvaliacaoFisica avaliacaoFisica = new AvaliacaoFisica();
        avaliacaoFisica.setAlunoId(aluno.getId());
        avaliacaoFisica.setPeso(request.peso());
        avaliacaoFisica.setAltura(request.altura());
        avaliacaoFisica.setPercentualGordura(request.percentualGordura());
        avaliacaoFisica.setAnotacoesMedicas(request.anotacoesMedicas());

        return avaliacaoFisicaRepository.save(avaliacaoFisica);
    }

    public List<AvaliacaoFisica> listarPorAluno(Long alunoId) {
        if (!alunoRepository.existsById(alunoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado");
        }
        return avaliacaoFisicaRepository.findByAlunoId(alunoId);
    }
}
