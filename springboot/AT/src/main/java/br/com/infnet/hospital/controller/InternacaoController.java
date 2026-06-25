package br.com.infnet.hospital.controller;

import br.com.infnet.hospital.dto.InternacaoRequest;
import br.com.infnet.hospital.dto.InternacaoResponse;
import br.com.infnet.hospital.service.InternacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internacoes")
public class InternacaoController {

    private final InternacaoService internacaoService;

    public InternacaoController(InternacaoService internacaoService) {
        this.internacaoService = internacaoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InternacaoResponse cadastrar(@Valid @RequestBody InternacaoRequest request) {
        return internacaoService.cadastrar(request);
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<InternacaoResponse> listarPorPaciente(@PathVariable Long pacienteId) {
        return internacaoService.listarPorPaciente(pacienteId);
    }
}
