package br.edu.infnet.order;

public class OrderNotFoundException extends RuntimeException {
  public OrderNotFoundException(Long id) {
    super("Pedido " + id + " não encontrado");
  }
}
