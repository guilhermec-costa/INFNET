package br.com.infnet.hospital.controller;

import br.com.infnet.hospital.dto.MedicoConsultaResumoResponse;
import br.com.infnet.hospital.dto.MedicoRequest;
import br.com.infnet.hospital.dto.MedicoResponse;
import br.com.infnet.hospital.service.MedicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicoResponse cadastrar(@Valid @RequestBody MedicoRequest request) {
        return medicoService.cadastrar(request);
    }

    @GetMapping
    public List<MedicoResponse> listar() {
        return medicoService.listar();
    }

    @PutMapping("/{id}")
    public MedicoResponse atualizar(@PathVariable Long id, @Valid @RequestBody MedicoRequest request) {
        return medicoService.atualizar(id, request);
    }

    @GetMapping("/ranking-consultas")
    public List<MedicoConsultaResumoResponse> rankingConsultas() {
        return medicoService.listarPorQuantidadeConsultas();
    }
}
