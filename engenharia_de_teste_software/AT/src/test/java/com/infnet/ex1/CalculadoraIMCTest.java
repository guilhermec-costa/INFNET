package com.infnet.ex1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da Calculadora de IMC")
class CalculoIMCTest {

  @Test
  @DisplayName("Deve calcular IMC corretamente para valores válidos típicos")
  void deveCalcularIMCCorretamente() {
    double imc = CalculoIMC.calcularPeso(70, 1.75);
    assertEquals(22.86, imc, 0.01);
  }

  @ParameterizedTest(name = "Peso={0}kg, Altura={1}m -> IMC={2}")
  @CsvSource({
      "50, 1.60, 19.53", // Saudável
      "80, 1.80, 24.69", // Saudável
      "100, 1.70, 34.60", // Obesidade Grau I
      "120, 1.65, 44.07", // Obesidade Grau III
      "45, 1.50, 20.00", // Saudável
      "60, 1.70, 20.76", // Saudável
  })
  @DisplayName("Deve calcular IMC para diversos cenários válidos")
  void deveCalcularIMCDiversosCenarios(double peso, double altura, double imcEsperado) {
    double imc = CalculoIMC.calcularPeso(peso, altura);
    assertEquals(imcEsperado, imc, 0.01);
  }

  @Test
  @DisplayName("Deve calcular IMC para valores extremos mas válidos")
  void deveCalcularIMCValoresExtremos() {
    double imc1 = CalculoIMC.calcularPeso(30, 1.40);
    assertEquals(15.31, imc1, 0.01);

    double imc2 = CalculoIMC.calcularPeso(150, 2.00);
    assertEquals(37.50, imc2, 0.01);
  }

  @Test
  @DisplayName("Deve lidar com valores decimais precisos")
  void deveLidarComValoresDecimaisPrecisos() {
    double imc = CalculoIMC.calcularPeso(70.5, 1.755);
    assertEquals(22.89, imc, 0.01);
  }


  @Test
  @DisplayName("Deve classificar IMC < 16.0 como 'Magreza grave'")
  void deveClassificarMagrezaGrave() {
    assertEquals("Magreza grave", CalculoIMC.classificarIMC(15.9));
    assertEquals("Magreza grave", CalculoIMC.classificarIMC(14.0));
    assertEquals("Magreza grave", CalculoIMC.classificarIMC(10.0));
  }

  @Test
  @DisplayName("Deve classificar IMC entre 16.0 e < 17.0 como 'Magreza moderada'")
  void deveClassificarMagrezaModerada() {
    assertEquals("Magreza moderada", CalculoIMC.classificarIMC(16.0));
    assertEquals("Magreza moderada", CalculoIMC.classificarIMC(16.5));
    assertEquals("Magreza moderada", CalculoIMC.classificarIMC(16.9));
  }

  @Test
  @DisplayName("Deve classificar IMC entre 17.0 e < 18.5 como 'Magreza leve'")
  void deveClassificarMagrezaLeve() {
    assertEquals("Magreza leve", CalculoIMC.classificarIMC(17.0));
    assertEquals("Magreza leve", CalculoIMC.classificarIMC(18.0));
    assertEquals("Magreza leve", CalculoIMC.classificarIMC(18.4));
  }

  @Test
  @DisplayName("Deve classificar IMC entre 18.5 e < 25.0 como 'Saudável'")
  void deveClassificarSaudavel() {
    assertEquals("Saudável", CalculoIMC.classificarIMC(18.5));
    assertEquals("Saudável", CalculoIMC.classificarIMC(21.0));
    assertEquals("Saudável", CalculoIMC.classificarIMC(24.9));
  }

  @Test
  @DisplayName("Deve classificar IMC entre 25.0 e < 30.0 como 'Sobrepeso'")
  void deveClassificarSobrepeso() {
    assertEquals("Sobrepeso", CalculoIMC.classificarIMC(25.0));
    assertEquals("Sobrepeso", CalculoIMC.classificarIMC(27.5));
    assertEquals("Sobrepeso", CalculoIMC.classificarIMC(29.9));
  }

  @Test
  @DisplayName("Deve classificar IMC entre 30.0 e < 35.0 como 'Obesidade Grau I'")
  void deveClassificarObesidadeGrauI() {
    assertEquals("Obesidade Grau I", CalculoIMC.classificarIMC(30.0));
    assertEquals("Obesidade Grau I", CalculoIMC.classificarIMC(32.5));
    assertEquals("Obesidade Grau I", CalculoIMC.classificarIMC(34.9));
  }

  @Test
  @DisplayName("Deve classificar IMC entre 35.0 e < 40.0 como 'Obesidade Grau II'")
  void deveClassificarObesidadeGrauII() {
    assertEquals("Obesidade Grau II", CalculoIMC.classificarIMC(35.0));
    assertEquals("Obesidade Grau II", CalculoIMC.classificarIMC(37.5));
    assertEquals("Obesidade Grau II", CalculoIMC.classificarIMC(39.9));
  }

  @Test
  @DisplayName("Deve classificar IMC >= 40.0 como 'Obesidade Grau III'")
  void deveClassificarObesidadeGrauIII() {
    assertEquals("Obesidade Grau III", CalculoIMC.classificarIMC(40.0));
    assertEquals("Obesidade Grau III", CalculoIMC.classificarIMC(45.0));
    assertEquals("Obesidade Grau III", CalculoIMC.classificarIMC(50.0));
  }


  @ParameterizedTest(name = "IMC {0} deve ser classificado como: {1}")
  @CsvSource({
      "15.99, Magreza grave",
      "16.00, Magreza moderada",
      "16.99, Magreza moderada",
      "17.00, Magreza leve",
      "18.49, Magreza leve",
      "18.50, Saudável",
      "24.99, Saudável",
      "25.00, Sobrepeso",
      "29.99, Sobrepeso",
      "30.00, Obesidade Grau I",
      "34.99, Obesidade Grau I",
      "35.00, Obesidade Grau II",
      "39.99, Obesidade Grau II",
      "40.00, Obesidade Grau III"
  })
  @DisplayName("Deve classificar corretamente nos valores limites entre categorias")
  void deveClassificarValoresLimite(double imc, String classificacaoEsperada) {
    String classificacao = CalculoIMC.classificarIMC(imc);
    assertEquals(classificacaoEsperada, classificacao);
  }


  @Test
  @DisplayName("Deve classificar IMC muito baixo (< 10)")
  void deveClassificarIMCMuitoBaixo() {
    assertEquals("Magreza grave", CalculoIMC.classificarIMC(5.0));
    assertEquals("Magreza grave", CalculoIMC.classificarIMC(8.5));
  }

  @Test
  @DisplayName("Deve classificar IMC muito alto (> 60)")
  void deveClassificarIMCMuitoAlto() {
    assertEquals("Obesidade Grau III", CalculoIMC.classificarIMC(60.0));
    assertEquals("Obesidade Grau III", CalculoIMC.classificarIMC(80.0));
  }

  @Test
  @DisplayName("Deve calcular IMC resultando em zero quando peso é zero")
  void deveCalcularIMCZeroQuandoPesoZero() {
    double imc = CalculoIMC.calcularPeso(0, 1.70);
    assertEquals(0.0, imc, 0.001);
  }

  @Test
  @DisplayName("Deve lidar com altura muito pequena (risco de divisão problemática)")
  void deveLidarComAlturaPequena() {
    double imc = CalculoIMC.calcularPeso(50, 0.1);
    assertEquals(5000.0, imc, 0.01);
  }

  @Test
  @DisplayName("Deve lidar com valores negativos (comportamento não especificado)")
  void deveLidarComValoresNegativos() {
    double imc = CalculoIMC.calcularPeso(-70, 1.75);
    assertTrue(imc < 0, "IMC com peso negativo resulta em valor negativo");
  }


  @Test
  @DisplayName("Deve manter precisão com números decimais longos")
  void deveManterPrecisaoComDecimaisLongos() {
    double imc = CalculoIMC.calcularPeso(70.123456789, 1.751234567);
    assertTrue(imc > 22.8 && imc < 23.0);
  }

  @Test
  @DisplayName("Deve calcular corretamente com valores muito próximos")
  void deveCalcularComValoresMuitoProximos() {
    double imc1 = CalculoIMC.calcularPeso(70.000, 1.750);
    double imc2 = CalculoIMC.calcularPeso(70.001, 1.750);

    assertNotEquals(imc1, imc2, "IMCs devem ser diferentes para pesos diferentes");
    assertTrue(Math.abs(imc1 - imc2) < 0.01, "Diferença deve ser muito pequena");
  }


  @Test
  @DisplayName("Deve classificar IMC exatamente nos limites das categorias")
  void deveClassificarExatamenteNosLimites() {
    assertEquals("Magreza moderada", CalculoIMC.classificarIMC(16.0));
    assertEquals("Magreza leve", CalculoIMC.classificarIMC(17.0));
    assertEquals("Saudável", CalculoIMC.classificarIMC(18.5));
    assertEquals("Sobrepeso", CalculoIMC.classificarIMC(25.0));
    assertEquals("Obesidade Grau I", CalculoIMC.classificarIMC(30.0));
    assertEquals("Obesidade Grau II", CalculoIMC.classificarIMC(35.0));
    assertEquals("Obesidade Grau III", CalculoIMC.classificarIMC(40.0));
  }

  @Test
  @DisplayName("Deve classificar valores ligeiramente abaixo dos limites")
  void deveClassificarLigeiramenteAbaixoDosLimites() {
    assertEquals("Magreza grave", CalculoIMC.classificarIMC(15.999));
    assertEquals("Magreza moderada", CalculoIMC.classificarIMC(16.999));
    assertEquals("Magreza leve", CalculoIMC.classificarIMC(18.499));
    assertEquals("Saudável", CalculoIMC.classificarIMC(24.999));
    assertEquals("Sobrepeso", CalculoIMC.classificarIMC(29.999));
    assertEquals("Obesidade Grau I", CalculoIMC.classificarIMC(34.999));
    assertEquals("Obesidade Grau II", CalculoIMC.classificarIMC(39.999));
  }

  @Test
  @DisplayName("Deve classificar valores ligeiramente acima dos limites")
  void deveClassificarLigeiramenteAcimaDosLimites() {
    assertEquals("Magreza moderada", CalculoIMC.classificarIMC(16.001));
    assertEquals("Magreza leve", CalculoIMC.classificarIMC(17.001));
    assertEquals("Saudável", CalculoIMC.classificarIMC(18.501));
    assertEquals("Sobrepeso", CalculoIMC.classificarIMC(25.001));
    assertEquals("Obesidade Grau I", CalculoIMC.classificarIMC(30.001));
    assertEquals("Obesidade Grau II", CalculoIMC.classificarIMC(35.001));
    assertEquals("Obesidade Grau III", CalculoIMC.classificarIMC(40.001));
  }
}
