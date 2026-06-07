package com.academia.poliglota.controller;

import com.academia.poliglota.dto.TreinoRequest;
import com.academia.poliglota.model.Treino;
import com.academia.poliglota.service.TreinoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/treinos")
public class TreinoController {

    private final TreinoService treinoService;

    public TreinoController(TreinoService treinoService) {
        this.treinoService = treinoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Treino cadastrar(@Valid @RequestBody TreinoRequest request) {
        return treinoService.cadastrar(request);
    }
}
