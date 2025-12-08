package com.infnet.service;

import com.infnet.domain.Entrega;
import com.infnet.frete.CalculadoraFrete;
import com.infnet.frete.PromocaoFrete;

public class EtiquetaService {
  private final CalculadoraFrete calculadoraFrete;
  private final PromocaoFrete promocaoFrete;

  public EtiquetaService(CalculadoraFrete calculadoraFrete, PromocaoFrete promocaoFrete) {
    this.calculadoraFrete = calculadoraFrete;
    this.promocaoFrete = promocaoFrete;
  }

  public String gerarEtiqueta(Entrega entrega) {
    double frete = calcularFreteComPromocao(entrega);
    return "Destinatario: " + entrega.getDestinatario() + "\nEndereco: " + entrega.getEndereco()
        + "\nValor do Frete: R$" + frete;
  }

  public String gerarResumoPedido(Entrega entrega) {
    double frete = calcularFreteComPromocao(entrega);
    return "Pedido para " + entrega.getDestinatario() + " com frete tipo " + entrega.getTipoFrete() + " no valor de R$"
        + frete;
  }

  private double calcularFreteComPromocao(Entrega entrega) {
    double freteBase = calculadoraFrete.calcular(entrega);
    return promocaoFrete.aplicarDesconto(entrega, freteBase);
  }

  public boolean isFreteGratis(Entrega entrega) {
    return calculadoraFrete.isFreteGratis(entrega);
  }
}