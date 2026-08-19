package com.example.notificacoes.service;

import com.example.notificacoes.dto.NotificacaoRequest;
import com.example.notificacoes.model.Notificacao;
import com.example.notificacoes.repository.NotificacaoRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;

    public NotificacaoService(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    public Notificacao criar(NotificacaoRequest request) {
        Notificacao notificacao = new Notificacao();
        notificacao.setLeitorId(request.leitorId());
        notificacao.setLeitorNome(request.leitorNome());
        notificacao.setTipo(request.tipo());
        notificacao.setTitulo(request.titulo());
        notificacao.setMensagem(request.mensagem());
        notificacao.setCriadaEm(LocalDateTime.now());
        return notificacaoRepository.save(notificacao);
    }

    @Transactional(readOnly = true)
    public List<Notificacao> listarPorLeitor(Long leitorId) {
        return notificacaoRepository.findByLeitorIdOrderByCriadaEmDesc(leitorId);
    }
}
