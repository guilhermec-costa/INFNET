package com.academia.poliglota.controller;

import com.academia.poliglota.dto.AvaliacaoFisicaRequest;
import com.academia.poliglota.model.AvaliacaoFisica;
import com.academia.poliglota.service.AvaliacaoFisicaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/avaliacoes-fisicas")
public class AvaliacaoFisicaController {

    private final AvaliacaoFisicaService avaliacaoFisicaService;

    public AvaliacaoFisicaController(AvaliacaoFisicaService avaliacaoFisicaService) {
        this.avaliacaoFisicaService = avaliacaoFisicaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvaliacaoFisica cadastrar(@Valid @RequestBody AvaliacaoFisicaRequest request) {
        return avaliacaoFisicaService.cadastrar(request);
    }

    @GetMapping("/aluno/{alunoId}")
    public List<AvaliacaoFisica> listarPorAluno(@PathVariable Long alunoId) {
        return avaliacaoFisicaService.listarPorAluno(alunoId);
    }
}
