package com.infnet.test_app;

import com.infnet.test_app.exception.ReembolsoNaoAutorizadoException;
import com.infnet.test_app.models.Consulta;
import com.infnet.test_app.repository.HistoricoConsultas;
import com.infnet.test_app.service.Auditoria;
import com.infnet.test_app.service.AutorizadorReembolso;
import com.infnet.test_app.service.PlanoSaude;

public class CalculadoraReembolso {

    private static final double TETO_REEMBOLSO = 150.0; // Regra da Etapa 11

    private final HistoricoConsultas historico;
    private final PlanoSaude planoSaude;
    private final Auditoria auditoria;
    private final AutorizadorReembolso autorizador;

    public CalculadoraReembolso(HistoricoConsultas historico, PlanoSaude planoSaude, Auditoria auditoria, AutorizadorReembolso autorizador) {
        this.historico = historico;
        this.planoSaude = planoSaude;
        this.auditoria = auditoria;
        this.autorizador = autorizador;
    }

    public double calcular(Consulta consulta) {
        // Etapa 8: Verifica se o reembolso está autorizado
        if (!autorizador.autorizar(consulta)) {
            throw new ReembolsoNaoAutorizadoException("Reembolso não autorizado para a consulta.");
        }

        // Etapa 5: Registra a consulta no histórico
        historico.registrar(consulta);

        // Etapa 6: Obtém o percentual de cobertura do plano de saúde
        double percentualCobertura = planoSaude.getPercentualCobertura(consulta.getPaciente());

        // Etapa 1: Lógica de cálculo principal
        double reembolsoCalculado = consulta.getValor() * (percentualCobertura / 100.0);

        // Etapa 11: Aplica a regra de teto
        double reembolsoFinal = Math.min(reembolsoCalculado, TETO_REEMBOLSO);

        // Etapa 7: Registra o cálculo final na auditoria
        auditoria.registrarCalculo(consulta, reembolsoFinal);

        return reembolsoFinal;
    }
}