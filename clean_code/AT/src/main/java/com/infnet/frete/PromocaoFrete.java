package com.infnet.frete;

import com.infnet.domain.Entrega;

public interface PromocaoFrete {
    double aplicarDesconto(Entrega entrega, double valorFrete);
}