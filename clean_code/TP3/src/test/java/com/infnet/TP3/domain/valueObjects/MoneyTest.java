package com.infnet.TP3.domain.valueObjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

/**
 * Testes para Money demonstrando:
 * - Precisão de BigDecimal
 * - Imutabilidade
 * - Validações
 */
class MoneyTest {

  @Test
  @DisplayName("EXERCÍCIO 4: BigDecimal mantém precisão em somas")
  void bigDecimalDeveMaterPrecisao() {
    Money valor1 = Money.brl("0.1");
    Money valor2 = Money.brl("0.2");

    Money resultado = valor1.add(valor2);

    assertEquals(new BigDecimal("0.30"), resultado.getAmount());
  }

  @Test
  @DisplayName("EXERCÍCIO 2: Operações devem retornar NOVO Money")
  void operacoesDevemRetornarNovoMoney() {
    Money original = Money.brl("100.00");
    Money comDesconto = original.applyDiscount(BigDecimal.valueOf(10));

    assertEquals(new BigDecimal("100.00"), original.getAmount(),
        "Valor original deve permanecer inalterado");

    assertEquals(new BigDecimal("90.00"), comDesconto.getAmount());
  }

  @Test
  @DisplayName("EXERCÍCIO 3: Não deve permitir valores negativos")
  void naoDevePermitirValoresNegativos() {
    assertThrows(IllegalArgumentException.class, () -> {
      Money.brl("-10.00");
    });
  }

  @Test
  @DisplayName("EXERCÍCIO 4: Deve validar moedas compatíveis em operações")
  void deveValidarMoedasCompativeis() {
    Money brl = Money.brl("100.00");
    Money usd = Money.of(
        new BigDecimal("100.00"),
        java.util.Currency.getInstance("USD"));

    assertThrows(IllegalArgumentException.class, () -> {
      brl.add(usd);
    }, "Não deve permitir operações entre moedas diferentes");
  }

  @Test
  @DisplayName("EXERCÍCIO 2: Multiply é método puro (sem efeitos colaterais)")
  void multiplyEMetodoPuro() {
    Money preco = Money.brl("50.00");

    Money total1 = preco.multiply(BigDecimal.valueOf(3));
    Money total2 = preco.multiply(BigDecimal.valueOf(3));

    assertEquals(total1.getAmount(), total2.getAmount());

    assertEquals(new BigDecimal("50.00"), preco.getAmount());
  }

  @Test
  @DisplayName("EXERCÍCIO 3: Validação de percentual em applyDiscount")
  void deveValidarPercentualDesconto() {
    Money valor = Money.brl("100.00");

    assertThrows(IllegalArgumentException.class, () -> {
      valor.applyDiscount(BigDecimal.valueOf(-10));
    });

    assertThrows(IllegalArgumentException.class, () -> {
      valor.applyDiscount(BigDecimal.valueOf(101));
    });
  }
}