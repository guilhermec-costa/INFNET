package br.com.infnet.hospital.controller;

import br.com.infnet.hospital.dto.ConsultaRequest;
import br.com.infnet.hospital.dto.ConsultaResponse;
import br.com.infnet.hospital.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaResponse cadastrar(@Valid @RequestBody ConsultaRequest request) {
        return consultaService.cadastrar(request);
    }
}
