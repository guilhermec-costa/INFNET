package com.engenharia_teste.TP1;

import net.jqwik.api.*;
import static org.assertj.core.api.Assertions.assertThat;

class CalculadoraIMCLimitesTest {

    @Example
    void testeLimiteExatoMagrezaGrave() {
        double imc = 15.99;
        String classif = CalculoIMC.classificarIMC(imc);
        assertThat(classif).isEqualTo("Magreza grave");
    }

    @Example
    void testeLimiteExatoMagrezaModerada() {
        double imc = 16.0;
        String classif = CalculoIMC.classificarIMC(imc);
        assertThat(classif).isEqualTo("Magreza moderada");
    }

    @Example
    void testeLimiteExatoSaudavel() {
        double imc = 18.5;
        String classif = CalculoIMC.classificarIMC(imc);
        assertThat(classif).isEqualTo("Saudável");
    }
    
    @Example
    void testeLimiteExatoSobrepeso() {
        double imc = 25.0;
        String classif = CalculoIMC.classificarIMC(imc);
        assertThat(classif).isEqualTo("Sobrepeso");
    }
    
    @Example
    void testeLimiteExatoObesidadeGrauIII() {
        double imc = 40.0;
        String classif = CalculoIMC.classificarIMC(imc);
        assertThat(classif).isEqualTo("Obesidade Grau III");
    }
}