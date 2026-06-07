package com.academia.poliglota.controller;

import com.academia.poliglota.dto.AlunoRankingResponse;
import com.academia.poliglota.dto.AlunoRequest;
import com.academia.poliglota.model.Aluno;
import com.academia.poliglota.service.AlunoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aluno cadastrar(@Valid @RequestBody AlunoRequest request) {
        return alunoService.cadastrar(request);
    }

    @GetMapping("/ativos")
    public List<Aluno> listarAtivos() {
        return alunoService.listarAtivos();
    }

    @GetMapping("/ranking")
    public List<AlunoRankingResponse> ranking() {
        return alunoService.buscarRanking();
    }
}
