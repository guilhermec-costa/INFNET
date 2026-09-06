package br.edu.infnet.order;

public class ProductUnavailableException extends RuntimeException {
  public ProductUnavailableException(Long id) {
    super("Produto " + id + " inexistente ou indisponível");
  }
}
