package br.com.logistica.fretes.application;

import br.com.logistica.fretes.domain.Cotacao;
import br.com.logistica.fretes.domain.Rota;
import br.com.logistica.sharedkernel.domain.Dinheiro;
import br.com.logistica.sharedkernel.domain.ModalTransporte;

import java.math.BigDecimal;
import java.time.Duration;

public class CotacaoFreteService {

    public Cotacao cotar(Rota rota) {
        BigDecimal base = switch (rota.modal()) {
            case MOTOBOY -> BigDecimal.valueOf(25);
            case CAMINHAO -> BigDecimal.valueOf(180);
            case TREM -> BigDecimal.valueOf(250);
            case NAVIO -> BigDecimal.valueOf(400);
        };

        Duration prazo = estimarPrazo(rota.modal());
        return new Cotacao(Dinheiro.de(base), prazo);
    }

    private Duration estimarPrazo(ModalTransporte modal) {
        return switch (modal) {
            case MOTOBOY -> Duration.ofHours(4);
            case CAMINHAO -> Duration.ofHours(36);
            case TREM -> Duration.ofHours(72);
            case NAVIO -> Duration.ofDays(10);
        };
    }
}
