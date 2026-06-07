package com.academia.poliglota.controller;

import com.academia.poliglota.dto.TokenAcessoResponse;
import com.academia.poliglota.dto.TokenValidacaoResponse;
import com.academia.poliglota.service.TokenAcessoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/acessos")
public class TokenAcessoController {

    private final TokenAcessoService tokenAcessoService;

    public TokenAcessoController(TokenAcessoService tokenAcessoService) {
        this.tokenAcessoService = tokenAcessoService;
    }

    @PostMapping("/tokens")
    public TokenAcessoResponse gerarToken(@RequestParam Long alunoId) {
        return tokenAcessoService.gerarToken(alunoId);
    }

    @GetMapping("/tokens/{token}/validar")
    public TokenValidacaoResponse validar(@PathVariable String token) {
        return tokenAcessoService.validarToken(token);
    }
}
