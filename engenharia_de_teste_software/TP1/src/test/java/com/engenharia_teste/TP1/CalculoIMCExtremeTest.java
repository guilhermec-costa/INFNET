package com.engenharia_teste.TP1;
import net.jqwik.api.*;
import static org.assertj.core.api.Assertions.assertThat;

class CalculoIMCExtremeTest {

    @Provide
    Arbitrary<Double> pesosExtremos() {
        return Arbitraries.doubles().between(30.0, 500.0);
    }

    @Provide
    Arbitrary<Double> alturasExtremas() {
        return Arbitraries.doubles().between(0.5, 2.8);
    }

    @Property
    void testIMCComValoresExtremos(
        @ForAll("pesosExtremos") double peso, 
        @ForAll("alturasExtremas") double altura
    ) {
        double imc = CalculoIMC.calcularPeso(peso, altura);
        String classificacao = CalculoIMC.classificarIMC(imc);

        assertThat(imc).isPositive();
        
        assertThat(classificacao).isNotNull().isNotEmpty();
    }
}