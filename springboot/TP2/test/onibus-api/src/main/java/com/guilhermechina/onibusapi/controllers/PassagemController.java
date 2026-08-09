package com.guilhermechina.onibusapi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.guilhermechina.onibusapi.dtos.PassagemRequestDTO;
import com.guilhermechina.onibusapi.dtos.PassagemResponseDTO;
import com.guilhermechina.onibusapi.services.PassagemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/passagens")
@RequiredArgsConstructor
public class PassagemController {

    private final PassagemService passagemService;

    @GetMapping
    public List<PassagemResponseDTO> listarTodas() {
        return passagemService.listarTodas();
    }

    @PostMapping
    public ResponseEntity<PassagemResponseDTO> criar(@RequestBody PassagemRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(passagemService.criar(requestDTO));
    }

    @GetMapping("/{id}")
    public PassagemResponseDTO buscarPorId(@PathVariable Long id) {
        return passagemService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public PassagemResponseDTO atualizar(@PathVariable Long id, @RequestBody PassagemRequestDTO requestDTO) {
        return passagemService.atualizar(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        passagemService.deletar(id);
    }

    @GetMapping("/busca")
    public List<PassagemResponseDTO> buscarPorDestino(@RequestParam String destino) {
        return passagemService.buscarPorDestino(destino);
    }
}
