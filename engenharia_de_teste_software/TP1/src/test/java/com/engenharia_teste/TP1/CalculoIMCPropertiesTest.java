package com.engenharia_teste.TP1;

import net.jqwik.api.*;
import net.jqwik.api.constraints.DoubleRange;
import net.jqwik.api.constraints.Positive;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assumptions;

class CalculoIMCPropertiesTest {

  @Property
  void imcNuncaDeveSerNegativo(
      @ForAll @DoubleRange(min = 1.0, max = 300.0) double peso,
      @ForAll @DoubleRange(min = 0.5, max = 2.5) double altura) {
    double imc = CalculoIMC.calcularPeso(peso, altura);

    assertThat(imc).isGreaterThan(0);
  }

  @Property
  void classificacaoDeveSerMonotonica(@ForAll @Positive double imc1, @ForAll @Positive double imc2) {
    Assumptions.assumeThat(imc1 < imc2);

    String class1 = CalculoIMC.classificarIMC(imc1);
    String class2 = CalculoIMC.classificarIMC(imc2);

    java.util.Map<String, Integer> ordem = java.util.Map.of(
        "Magreza grave", 0,
        "Magreza moderada", 1,
        "Magreza leve", 2,
        "Saudável", 3,
        "Sobrepeso", 4,
        "Obesidade Grau I", 5,
        "Obesidade Grau II", 6,
        "Obesidade Grau III", 7);

    assertThat(ordem.get(class1)).isLessThanOrEqualTo(ordem.get(class2));
  }
}