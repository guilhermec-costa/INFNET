package com.infnet.frete;

import com.infnet.domain.Entrega;

public class FreteEconomico implements CalculadoraFrete {
  private static final double TAXA_POR_KG = 1.1;
  private static final double DESCONTO_FIXO = 5.0;
  private static final double LIMITE_GRATIS = 2.0;

  @Override
  public double calcular(Entrega entrega) {
    return entrega.getPeso() * TAXA_POR_KG - DESCONTO_FIXO;
  }

  @Override
  public boolean isFreteGratis(Entrega entrega) {
    return entrega.getPeso() < LIMITE_GRATIS;
  }
}