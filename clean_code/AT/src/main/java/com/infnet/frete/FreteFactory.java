package com.infnet.frete;

import com.infnet.exception.InvalidPesoException;

public class FreteFactory {
  public static CalculadoraFrete getCalculadora(String tipoFrete) {
    switch (tipoFrete) {
      case "EXP":
        return new FreteExpresso();
      case "PAD":
        return new FretePadrao();
      case "ECO":
        return new FreteEconomico();
      default:
        throw new InvalidPesoException("Tipo de frete desconhecido: " + tipoFrete);
    }
  }
}