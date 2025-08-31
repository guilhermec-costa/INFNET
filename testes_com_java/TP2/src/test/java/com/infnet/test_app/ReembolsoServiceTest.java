package com.infnet.test_app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReembolsoServiceTest {
    private final CalculadoraReembolso service = new CalculadoraReembolso();

    @Test
    void deveCalcularReembolsoCorretamente() {
        double resultado = service.calcularReembolso(200.0, 70.0);
        assertEquals(140.0, resultado, 0.0001);
    }

    @Test
    void deveRetornarZeroQuandoValorConsultaForZero() {
        double resultado = service.calcularReembolso(0.0, 70.0);
        assertEquals(0.0, resultado, 0.0001, 
            "Se a consulta for 0, o reembolso deve ser 0");
    }

    @Test
    void deveRetornarZeroQuandoCoberturaForZero() {
        double resultado = service.calcularReembolso(200.0, 0.0);
        assertEquals(0.0, resultado, 0.0001,
            "Com cobertura 0%, reembolso deve ser 0");
    }

    @Test
    void deveRetornarValorTotalQuandoCoberturaFor100() {
        double resultado = service.calcularReembolso(200.0, 100.0);
        assertEquals(200.0, resultado, 0.0001,
            "Com cobertura 100%, o reembolso deve ser igual ao valor da consulta");
    }

    @Test
    void deveRetornarZeroQuandoValorEPercentualForemZero() {
        double resultado = service.calcularReembolso(0.0, 0.0);
        assertEquals(0.0, resultado, 0.0001,
            "Com consulta 0 e cobertura 0%, resultado deve ser 0");
    } 
}