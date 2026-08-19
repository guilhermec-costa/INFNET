package com.example.notificacoes.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.notificacoes.dto.NotificacaoRequest;
import com.example.notificacoes.model.Notificacao;
import com.example.notificacoes.model.TipoNotificacao;
import com.example.notificacoes.repository.NotificacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificacaoServiceTest {
    @Mock private NotificacaoRepository notificacaoRepository;
    @InjectMocks private NotificacaoService notificacaoService;

    @Test
    void devePersistirNotificacaoComDataDeCriacao() {
        when(notificacaoRepository.save(any(Notificacao.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Notificacao criada = notificacaoService.criar(new NotificacaoRequest(
                7L, "Ana Costa", TipoNotificacao.EMPRESTIMO_REGISTRADO,
                "Empréstimo registrado", "Devolução prevista para amanhã."
        ));
        ArgumentCaptor<Notificacao> captor = ArgumentCaptor.forClass(Notificacao.class);
        verify(notificacaoRepository).save(captor.capture());
        assertThat(captor.getValue().getCriadaEm()).isNotNull();
        assertThat(criada.getLeitorId()).isEqualTo(7L);
    }
}
