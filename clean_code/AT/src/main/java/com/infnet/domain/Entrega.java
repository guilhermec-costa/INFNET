package com.infnet.domain;

import com.infnet.exception.InvalidEnderecoException;
import com.infnet.exception.InvalidPesoException;

public final class Entrega {
  private final String endereco;
  private final double peso;
  private final String tipoFrete;
  private final String destinatario;

  public Entrega(String endereco, double peso, String tipoFrete, String destinatario) {
    validateEndereco(endereco);
    validatePeso(peso);
    validateTipoFrete(tipoFrete);
    validateDestinatario(destinatario);

    this.endereco = endereco;
    this.peso = peso;
    this.tipoFrete = tipoFrete;
    this.destinatario = destinatario;
  }

  private void validateEndereco(String endereco) {
    if (endereco == null || endereco.trim().isEmpty()) {
      throw new InvalidEnderecoException("Endereco nao pode ser nulo ou vazio.");
    }
  }

  private void validatePeso(double peso) {
    if (peso <= 0) {
      throw new InvalidPesoException("Peso deve ser maior que zero.");
    }
  }

  private void validateTipoFrete(String tipoFrete) {
    if (tipoFrete == null || (!tipoFrete.equals("EXP") && !tipoFrete.equals("PAD") && !tipoFrete.equals("ECO"))) {
      throw new InvalidPesoException("Tipo de frete invalido. Deve ser EXP, PAD ou ECO.");
    }
  }

  private void validateDestinatario(String destinatario) {
    if (destinatario == null || destinatario.trim().isEmpty()) {
      throw new IllegalArgumentException("Destinatario nao pode ser nulo ou vazio.");
    }
  }

  public String getEndereco() {
    return endereco;
  }

  public double getPeso() {
    return peso;
  }

  public String getTipoFrete() {
    return tipoFrete;
  }

  public String getDestinatario() {
    return destinatario;
  }
}