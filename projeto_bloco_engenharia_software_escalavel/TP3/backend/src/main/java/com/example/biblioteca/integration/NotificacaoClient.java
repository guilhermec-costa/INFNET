package com.example.biblioteca.integration;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notificacoes-service", url = "${notificacoes.service.url}")
public interface NotificacaoClient {

    @PostMapping("/api/notificacoes")
    NotificacaoResponse criar(@RequestBody NotificacaoRequest request);

    @GetMapping("/api/notificacoes/leitor/{leitorId}")
    List<NotificacaoResponse> listarPorLeitor(@PathVariable Long leitorId);
}
