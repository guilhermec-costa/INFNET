package com.infnet.ex1;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Testes para a Calculadora de IMC
 * Aplicando técnicas de Partição de Equivalência e Análise de Valor Limite
 */
@DisplayName("Testes da Calculadora de IMC")
class CalculadoraIMCTest {

  private CalculadoraIMC calculadora;

  @BeforeEach
  void setUp() {
    calculadora = new CalculadoraIMC();
  }

  // ===== TESTES DE CÁLCULO DE IMC =====

  @Test
  @DisplayName("Deve calcular IMC corretamente para valores válidos típicos")
  void deveCalcularIMCCorretamente() {
    // Peso: 70kg, Altura: 1.75m -> IMC = 22.86
    double imc = calculadora.calcularIMC(70, 1.75);
    assertEquals(22.86, imc, 0.01);
  }

  @ParameterizedTest(name = "Peso={0}kg, Altura={1}m -> IMC={2}")
  @CsvSource({
      "50, 1.60, 19.53", // Peso normal
      "80, 1.80, 24.69", // Peso normal (limite superior)
      "100, 1.70, 34.60", // Obesidade Grau I
      "120, 1.65, 44.07", // Obesidade Grau III
      "45, 1.50, 20.00", // Peso normal
  })
  @DisplayName("Deve calcular IMC para diversos cenários válidos")
  void deveCalcularIMCDiversosCenarios(double peso, double altura, double imcEsperado) {
    double imc = calculadora.calcularIMC(peso, altura);
    assertEquals(imcEsperado, imc, 0.01);
  }

  @Test
  @DisplayName("Deve aceitar peso mínimo válido (0.1kg)")
  void deveAceitarPesoMinimoValido() {
    assertDoesNotThrow(() -> calculadora.calcularIMC(0.1, 1.70));
  }

  @Test
  @DisplayName("Deve aceitar peso máximo válido (500kg)")
  void deveAceitarPesoMaximoValido() {
    assertDoesNotThrow(() -> calculadora.calcularIMC(500, 1.70));
  }

  @Test
  @DisplayName("Deve rejeitar peso zero")
  void deveRejeitarPesoZero() {
    Exception exception = assertThrows(IllegalArgumentException.class,
        () -> calculadora.calcularIMC(0, 1.70));
    assertEquals("Peso deve ser maior que zero", exception.getMessage());
  }

  @Test
  @DisplayName("Deve rejeitar peso negativo")
  void deveRejeitarPesoNegativo() {
    Exception exception = assertThrows(IllegalArgumentException.class,
        () -> calculadora.calcularIMC(-10, 1.70));
    assertEquals("Peso deve ser maior que zero", exception.getMessage());
  }

  @Test
  @DisplayName("Deve rejeitar peso acima do limite (>500kg)")
  void deveRejeitarPesoExcessivo() {
    Exception exception = assertThrows(IllegalArgumentException.class,
        () -> calculadora.calcularIMC(501, 1.70));
    assertEquals("Peso inválido: valor muito alto", exception.getMessage());
  }

  @Test
  @DisplayName("Deve aceitar altura mínima válida (0.5m)")
  void deveAceitarAlturaMinimaValida() {
    assertDoesNotThrow(() -> calculadora.calcularIMC(70, 0.5));
  }

  @Test
  @DisplayName("Deve aceitar altura máxima válida (3.0m)")
  void deveAceitarAlturaMaximaValida() {
    assertDoesNotThrow(() -> calculadora.calcularIMC(70, 3.0));
  }

  @Test
  @DisplayName("Deve rejeitar altura zero")
  void deveRejeitarAlturaZero() {
    Exception exception = assertThrows(IllegalArgumentException.class,
        () -> calculadora.calcularIMC(70, 0));
    assertEquals("Altura deve ser maior que zero", exception.getMessage());
  }

  @Test
  @DisplayName("Deve rejeitar altura negativa")
  void deveRejeitarAlturaNegativa() {
    Exception exception = assertThrows(IllegalArgumentException.class,
        () -> calculadora.calcularIMC(70, -1.70));
    assertEquals("Altura deve ser maior que zero", exception.getMessage());
  }

  @Test
  @DisplayName("Deve rejeitar altura muito baixa (<0.5m)")
  void deveRejeitarAlturaMuitoBaixa() {
    Exception exception = assertThrows(IllegalArgumentException.class,
        () -> calculadora.calcularIMC(70, 0.49));
    assertEquals("Altura inválida: valor muito baixo", exception.getMessage());
  }

  @Test
  @DisplayName("Deve rejeitar altura muito alta (>3.0m)")
  void deveRejeitarAlturaMuitoAlta() {
    Exception exception = assertThrows(IllegalArgumentException.class,
        () -> calculadora.calcularIMC(70, 3.01));
    assertEquals("Altura inválida: valor muito alto", exception.getMessage());
  }

  @ParameterizedTest(name = "IMC {0} deve ser classificado como: {1}")
  @CsvSource({
      "16.0, Abaixo do peso",
      "18.4, Abaixo do peso",
      "18.5, Peso normal",
      "22.0, Peso normal",
      "24.9, Peso normal",
      "25.0, Sobrepeso",
      "27.5, Sobrepeso",
      "29.9, Sobrepeso",
      "30.0, Obesidade Grau I",
      "32.5, Obesidade Grau I",
      "34.9, Obesidade Grau I",
      "35.0, Obesidade Grau II",
      "37.5, Obesidade Grau II",
      "39.9, Obesidade Grau II",
      "40.0, Obesidade Grau III",
      "45.0, Obesidade Grau III"
  })
  @DisplayName("Deve classificar IMC corretamente em todas as faixas")
  void deveClassificarIMCCorretamente(double imc, String classificacaoEsperada) {
    String classificacao = calculadora.classificarIMC(imc);
    assertEquals(classificacaoEsperada, classificacao);
  }

  @Test
  @DisplayName("Deve rejeitar IMC negativo na classificação")
  void deveRejeitarIMCNegativoNaClassificacao() {
    Exception exception = assertThrows(IllegalArgumentException.class,
        () -> calculadora.classificarIMC(-1.0));
    assertEquals("IMC não pode ser negativo", exception.getMessage());
  }

  @Test
  @DisplayName("Deve calcular e classificar corretamente em fluxo completo")
  void deveCalcularEClassificarCorretamente() {
    String resultado = calculadora.calcularEClassificar(70, 1.75);
    assertTrue(resultado.contains("22.86"));
    assertTrue(resultado.contains("Peso normal"));
  }

  @ParameterizedTest(name = "Peso={0}kg, Altura={1}m -> {2}")
  @CsvSource({
      "45, 1.60, Abaixo do peso",
      "65, 1.70, Peso normal",
      "85, 1.75, Sobrepeso",
      "95, 1.70, Obesidade Grau I",
      "110, 1.70, Obesidade Grau II",
      "130, 1.70, Obesidade Grau III"
  })
  @DisplayName("Deve processar fluxo completo para diversos cenários")
  void deveProcessarFluxoCompletoDiversosCenarios(double peso, double altura, String classificacaoEsperada) {
    String resultado = calculadora.calcularEClassificar(peso, altura);
    assertTrue(resultado.contains(classificacaoEsperada));
  }
}
