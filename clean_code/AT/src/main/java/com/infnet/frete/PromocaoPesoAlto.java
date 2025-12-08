package com.infnet.frete;

import com.infnet.domain.Entrega;

public class PromocaoPesoAlto implements PromocaoFrete {
  private static final double LIMITE_PESO = 10.0;
  private static final double DESCONTO_PESO = 1.0;

  @Override
  public double aplicarDesconto(Entrega entrega, double valorFrete) {
    if (entrega.getPeso() > LIMITE_PESO) {
      double pesoAjustado = entrega.getPeso() - DESCONTO_PESO;
      return valorFrete * (pesoAjustado / entrega.getPeso());
    }
    return valorFrete;
  }
}