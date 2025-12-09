package com.infnet.ex1;

/**
 * Calculadora de IMC
 * https://github.com/Wolfterro/Projetos-em-Java/tree/master/CalculoIMC
 */
public class CalculoIMC {

  /**
   * Classifica o IMC de acordo com as faixas estabelecidas
   * 
   * @param imc Valor do IMC calculado
   * @return Classificação do IMC
   */
  public static String classificarIMC(double imc) {
    if (imc < 16.0) {
      return "Magreza grave";
    } else if (imc == 16.0 || imc < 17.0) {
      return "Magreza moderada";
    } else if (imc == 17.0 || imc < 18.5) {
      return "Magreza leve";
    } else if (imc == 18.5 || imc < 25.0) {
      return "Saudável";
    } else if (imc == 25.0 || imc < 30.0) {
      return "Sobrepeso";
    } else if (imc == 30.0 || imc < 35.0) {
      return "Obesidade Grau I";
    } else if (imc == 35.0 || imc < 40.0) {
      return "Obesidade Grau II";
    } else {
      return "Obesidade Grau III";
    }
  }

  /**
   * Calcula o IMC com base no peso e altura
   * 
   * @param peso   Peso em quilogramas
   * @param altura Altura em metros
   * @return Valor do IMC calculado
   */
  public static double calcularPeso(double peso, double altura) {
    return peso / (altura * altura);
  }
}