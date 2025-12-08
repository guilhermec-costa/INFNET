package com.infnet.frete;

import com.infnet.domain.Entrega;

public class FreteExpresso implements CalculadoraFrete {
  private static final double TAXA_POR_KG = 1.5;
  private static final double TAXA_FIXA = 10.0;

  @Override
  public double calcular(Entrega entrega) {
    return entrega.getPeso() * TAXA_POR_KG + TAXA_FIXA;
  }

  @Override
  public boolean isFreteGratis(Entrega entrega) {
    return false;
  }
}