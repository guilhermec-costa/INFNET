package com.infnet.ex1;

/**
 * Calculadora de IMC (Índice de Massa Corporal)
 * Baseada no projeto:
 * https://github.com/Wolfterro/Projetos-em-Java/tree/master/CalculoIMC
 */
public class CalculadoraIMC {

  /**
   * Calcula o IMC com base no peso e altura
   * 
   * @param peso   Peso em quilogramas
   * @param altura Altura em metros
   * @return Valor do IMC calculado
   * @throws IllegalArgumentException se os valores forem inválidos
   */
  public double calcularIMC(double peso, double altura) {
    validarEntrada(peso, altura);
    return peso / (altura * altura);
  }

  /**
   * Classifica o IMC de acordo com a tabela da OMS
   * 
   * @param imc Valor do IMC
   * @return Classificação do IMC
   */
  public String classificarIMC(double imc) {
    if (imc < 0) {
      throw new IllegalArgumentException("IMC não pode ser negativo");
    }

    if (imc < 18.5) {
      return "Abaixo do peso";
    } else if (imc < 25.0) {
      return "Peso normal";
    } else if (imc < 30.0) {
      return "Sobrepeso";
    } else if (imc < 35.0) {
      return "Obesidade Grau I";
    } else if (imc < 40.0) {
      return "Obesidade Grau II";
    } else {
      return "Obesidade Grau III";
    }
  }

  /**
   * Calcula o IMC e retorna a classificação
   * 
   * @param peso   Peso em quilogramas
   * @param altura Altura em metros
   * @return Classificação do IMC
   */
  public String calcularEClassificar(double peso, double altura) {
    double imc = calcularIMC(peso, altura);
    return String.format("IMC: %.2f - %s", imc, classificarIMC(imc));
  }

  /**
   * Valida as entradas de peso e altura
   */
  private void validarEntrada(double peso, double altura) {
    if (peso <= 0) {
      throw new IllegalArgumentException("Peso deve ser maior que zero");
    }
    if (altura <= 0) {
      throw new IllegalArgumentException("Altura deve ser maior que zero");
    }
    if (peso > 500) {
      throw new IllegalArgumentException("Peso inválido: valor muito alto");
    }
    if (altura > 3.0) {
      throw new IllegalArgumentException("Altura inválida: valor muito alto");
    }
    if (altura < 0.5) {
      throw new IllegalArgumentException("Altura inválida: valor muito baixo");
    }
  }
}
