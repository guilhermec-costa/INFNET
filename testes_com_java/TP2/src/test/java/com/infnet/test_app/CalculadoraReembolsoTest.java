package com.infnet.test_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.infnet.test_app.exception.ReembolsoNaoAutorizadoException;
import com.infnet.test_app.helper.ConsultaHelper;
import com.infnet.test_app.models.Consulta;
import com.infnet.test_app.models.Paciente;
import com.infnet.test_app.repository.HistoricoConsultas;
import com.infnet.test_app.service.Auditoria;
import com.infnet.test_app.service.AutorizadorReembolso;
import com.infnet.test_app.service.PlanoSaude;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Habilita o Mockito para os testes
class CalculadoraReembolsoTest {

    // Etapa 8: Usando Mockito para criar mocks das dependências
    @Mock
    private HistoricoConsultas historicoMock;
    @Mock
    private PlanoSaude planoSaudeMock;
    @Mock
    private Auditoria auditoriaMock;
    @Mock
    private AutorizadorReembolso autorizadorMock;

    @InjectMocks
    private CalculadoraReembolso calculadora;

    @BeforeEach
    void setUp() {
        // Garante que a autorização seja verdadeira por padrão na maioria dos testes.
        // Testes específicos podem sobrescrever esse comportamento.
        when(autorizadorMock.autorizar(any(Consulta.class))).thenReturn(true);
    }

    // Etapa 1 e 3: Teste do reembolso básico em classe dedicada
    @Test
    @DisplayName("[Etapa 1] Deve calcular 70% de R$200 resultando em R$140")
    void deveCalcularReembolsoBasicoCorretamente() {
        Consulta consulta = ConsultaHelper.criarConsulta("João", 200.0);
        when(planoSaudeMock.getPercentualCobertura(any())).thenReturn(70.0);

        double valorReembolsado = calculadora.calcular(consulta);

        // O teto de 150 é maior que 140, então o resultado deve ser 140.
        assertEquals(140.0, valorReembolsado);
    }

    // Etapa 2: Teste de casos de borda
    @Test
    @DisplayName("[Etapa 2] Deve retornar 0 quando o percentual de cobertura for 0")
    void deveRetornarZeroParaCoberturaZero() {
        Consulta consulta = ConsultaHelper.criarConsulta("Maria", 250.0);
        when(planoSaudeMock.getPercentualCobertura(any())).thenReturn(0.0);

        double valorReembolsado = calculadora.calcular(consulta);

        assertEquals(0.0, valorReembolsado);
    }

    // Etapa 5: Teste com um Fake (simulado aqui com Mockito.verify)
    @Test
    @DisplayName("[Etapa 5] Deve registrar a consulta no histórico ao calcular")
    void deveRegistrarConsultaNoHistorico() {
        Consulta consulta = ConsultaHelper.consultaPadrao();
        when(planoSaudeMock.getPercentualCobertura(any())).thenReturn(50.0);

        calculadora.calcular(consulta);

        // Verificamos se o método 'registrar' foi chamado exatamente 1 vez
        verify(historicoMock, times(1)).registrar(consulta);
    }

    // Etapa 6: Teste com um Stub
    // Classe interna que atua como um Stub, sempre retornando 80%
    private static class PlanoSaudeStub80 implements PlanoSaude {
        @Override
        public double getPercentualCobertura(Paciente paciente) {
            return 80.0;
        }
    }

    @Test
    @DisplayName("[Etapa 6] Deve usar o percentual de cobertura do plano de saúde (Stub)")
    void deveCalcularComBaseNoPlanoDeSaudeStub() {
        PlanoSaude planoStub = new PlanoSaudeStub80();
        // Criamos uma nova instância da calculadora para usar nosso Stub real
        CalculadoraReembolso calculadoraComStub = new CalculadoraReembolso(historicoMock, planoStub, auditoriaMock, autorizadorMock);
        Consulta consulta = ConsultaHelper.criarConsulta("Carlos", 100.0); // 100 * 80% = 80

        double valorReembolsado = calculadoraComStub.calcular(consulta);

        assertEquals(80.0, valorReembolsado);
    }
    
    // Etapa 7: Teste com um Spy (simulado aqui com Mockito.verify)
    @Test
    @DisplayName("[Etapa 7] Deve chamar o serviço de auditoria ao calcular")
    void deveChamarServicoDeAuditoria() {
        Consulta consulta = ConsultaHelper.criarConsulta("Ana", 100.0);
        when(planoSaudeMock.getPercentualCobertura(any())).thenReturn(50.0); // Reembolso de 50.0

        calculadora.calcular(consulta);

        // Verificamos que a auditoria foi chamada com os parâmetros corretos
        verify(auditoriaMock).registrarCalculo(consulta, 50.0);
    }

    // Etapa 8: Teste de Mock para exceção
    @Test
    @DisplayName("[Etapa 8] Deve lançar exceção quando reembolso não for autorizado")
    void deveLancarExcecaoParaReembolsoNaoAutorizado() {
        Consulta consulta = ConsultaHelper.consultaPadrao();
        // Sobrescrevemos o comportamento padrão para este teste
        when(autorizadorMock.autorizar(consulta)).thenReturn(false);

        // Verificamos se a exceção correta é lançada
        assertThrows(ReembolsoNaoAutorizadoException.class, () -> {
            calculadora.calcular(consulta);
        });
    }
    
    // Etapa 10: Teste com margem de erro
    @Test
    @DisplayName("[Etapa 10] Deve comparar doubles com margem de erro")
    void deveCompararDoublesComMargemDeErro() {
        Consulta consulta = ConsultaHelper.criarConsulta("Pedro", 100.0);
        when(planoSaudeMock.getPercentualCobertura(any())).thenReturn(33.333);

        double valorReembolsado = calculadora.calcular(consulta);
        
        // assertEquals(33.333, valorReembolsado) poderia falhar
        assertEquals(33.333, valorReembolsado, 0.01); // O terceiro parâmetro é a margem
    }

    // Etapa 11: Teste da regra de teto (TDD)
    @Test
    @DisplayName("[Etapa 11] Deve aplicar o teto de R$150 para reembolsos altos")
    void deveAplicarTetoDeReembolso() {
        // 300 * 80% = 240, que é maior que o teto de 150
        Consulta consultaCara = ConsultaHelper.criarConsulta("Ricardo", 300.0);
        when(planoSaudeMock.getPercentualCobertura(any())).thenReturn(80.0);

        double valorReembolsado = calculadora.calcular(consultaCara);

        assertEquals(150.0, valorReembolsado);
        
        // Verificamos também que a auditoria registrou o valor com o teto
        verify(auditoriaMock).registrarCalculo(consultaCara, 150.0);
    }
    
    // Etapa 12: Teste de integração com múltiplos dublês
    @Test
    @DisplayName("[Etapa 12] Deve integrar todos os componentes em um cenário completo")
    void deveIntegrarComponentesCorretamente() {
        // 1. Helper para criar consulta
        Consulta consulta = ConsultaHelper.criarConsulta("Mariana", 500.0);
        
        // 2. Mock (Mockito) para autorização
        when(autorizadorMock.autorizar(consulta)).thenReturn(true);
        
        // 3. Stub (classe interna) para o plano de saúde (80% de cobertura)
        // 500 * 80% = 400. Deveria ser limitado pelo teto de 150.
        PlanoSaude planoStub = new PlanoSaudeStub80();
        CalculadoraReembolso calculadoraIntegrada = new CalculadoraReembolso(historicoMock, planoStub, auditoriaMock, autorizadorMock);

        // Ação
        double valorReembolsado = calculadoraIntegrada.calcular(consulta);

        // Verificação
        assertEquals(150.0, valorReembolsado, "O valor deveria ser travado no teto de R$150");

        // 4. Verificação de interações com os Mocks
        verify(historicoMock).registrar(consulta);
        verify(auditoriaMock).registrarCalculo(consulta, 150.0);
        verify(autorizadorMock).autorizar(consulta);
        // Garantir que nenhuma interação inesperada ocorreu
        verifyNoMoreInteractions(historicoMock, auditoriaMock, autorizadorMock);
    }
}