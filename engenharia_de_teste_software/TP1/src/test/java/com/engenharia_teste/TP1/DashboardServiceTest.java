package com.engenharia_teste.TP1;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    CalculadoraIMCService calculadoraMock;

    @InjectMocks
    DashboardService dashboardService;

    @Test
    void testMensagemQuandoEstiverSaudavel() {
        when(calculadoraMock.calcularPeso(70, 1.75)).thenReturn(22.8);
        when(calculadoraMock.classificarIMC(22.8)).thenReturn("Saudável");

        String mensagem = dashboardService.getMensagemSaude(70, 1.75);

        assertThat(mensagem).isEqualTo("Parabéns! Você está saudável.");
        
        verify(calculadoraMock).calcularPeso(70, 1.75);
        verify(calculadoraMock).classificarIMC(22.8);
    }

    @Test
    void testMensagemQuandoEstiverComSobrepeso() {
        when(calculadoraMock.calcularPeso(90, 1.75)).thenReturn(29.4);
        when(calculadoraMock.classificarIMC(29.4)).thenReturn("Sobrepeso");

        String mensagem = dashboardService.getMensagemSaude(90, 1.75);

        assertThat(mensagem).isEqualTo("Atenção! Risco de sobrepeso.");
    }
    
    @Test
    void testMensagemQuandoEstiverComMagreza() {
        when(calculadoraMock.calcularPeso(40, 1.75)).thenReturn(13.1);
        when(calculadoraMock.classificarIMC(13.1)).thenReturn("Magreza grave");

        String mensagem = dashboardService.getMensagemSaude(40, 1.75);

        assertThat(mensagem).isEqualTo("Atenção! Risco de desnutrição.");
    }
}