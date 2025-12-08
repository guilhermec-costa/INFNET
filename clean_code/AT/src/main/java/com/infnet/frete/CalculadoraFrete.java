package com.infnet.frete;

import com.infnet.domain.Entrega;

public interface CalculadoraFrete {
    double calcular(Entrega entrega);
    boolean isFreteGratis(Entrega entrega);
}