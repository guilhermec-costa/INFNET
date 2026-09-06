package br.edu.infnet.product;

public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException(Long id) {
    super("Produto " + id + " não encontrado");
  }
}
