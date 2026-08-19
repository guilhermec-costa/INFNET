package com.example.notificacoes.repository;

import com.example.notificacoes.model.Notificacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    List<Notificacao> findByLeitorIdOrderByCriadaEmDesc(Long leitorId);
}
