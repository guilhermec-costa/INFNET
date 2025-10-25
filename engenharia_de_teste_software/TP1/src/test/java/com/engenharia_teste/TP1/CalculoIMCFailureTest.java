package com.engenharia_teste.TP1;

import net.jqwik.api.*;
import static org.assertj.core.api.Assertions.assertThat;

class CalculoIMCFailureTest {

    @Property
    void testIMCComValoresAleatorios(@ForAll double peso, @ForAll double altura) {
        double imc = CalculoIMC.calcularPeso(peso, altura);
        
        String classificacao = CalculoIMC.classificarIMC(imc);

        if (classificacao.equals("Obesidade Grau III")) {
            assertThat(imc).isGreaterThanOrEqualTo(40.0);
        }
    }
}