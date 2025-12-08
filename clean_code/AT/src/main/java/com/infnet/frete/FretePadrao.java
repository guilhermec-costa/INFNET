package com.infnet.frete;

import com.infnet.domain.Entrega;

public class FretePadrao implements CalculadoraFrete {
    private static final double TAXA_POR_KG = 1.2;

    @Override
    public double calcular(Entrega entrega) {
        return entrega.getPeso() * TAXA_POR_KG;
    }

    @Override
    public boolean isFreteGratis(Entrega entrega) {
        return false;
    }
}