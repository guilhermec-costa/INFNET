package com.infnet.frete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.infnet.domain.Entrega;

class FreteExpressoTest {

  @Test
  void deveCalcularFreteExpresso() {
    CalculadoraFrete calculadora = new FreteExpresso();
    Entrega entrega = new Entrega("Rua A", 5.0, "EXP", "Joao");
    assertEquals(17.5, calculadora.calcular(entrega)); // 5*1.5 + 10
  }
}
