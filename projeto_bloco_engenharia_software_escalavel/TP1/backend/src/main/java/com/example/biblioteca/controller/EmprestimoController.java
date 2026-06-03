package com.example.biblioteca.controller;

import com.example.biblioteca.dto.EmprestimoRequest;
import com.example.biblioteca.dto.EmprestimoResponse;
import com.example.biblioteca.model.Emprestimo;
import com.example.biblioteca.service.EmprestimoService;
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
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public List<EmprestimoResponse> listarTodos() {
        return emprestimoService.listarTodos().stream().map(this::toResponse).toList();
    }

    @GetMapping("/ativos")
    public List<EmprestimoResponse> listarAtivos() {
        return emprestimoService.listarAtivos().stream().map(this::toResponse).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmprestimoResponse registrarEmprestimo(@Valid @RequestBody EmprestimoRequest request) {
        return toResponse(emprestimoService.registrarEmprestimo(request));
    }

    @PostMapping("/{id}/devolucao")
    public EmprestimoResponse registrarDevolucao(@PathVariable Long id) {
        return toResponse(emprestimoService.registrarDevolucao(id));
    }

    private EmprestimoResponse toResponse(Emprestimo emprestimo) {
        return new EmprestimoResponse(
                emprestimo.getId(),
                emprestimo.getLivro().getId(),
                emprestimo.getLivro().getTitulo(),
                emprestimo.getLeitor().getId(),
                emprestimo.getLeitor().getNome(),
                emprestimo.getDataEmprestimo(),
                emprestimo.getDataPrevistaDevolucao(),
                emprestimo.getDataDevolucao(),
                emprestimo.isAtivo()
        );
    }
}
