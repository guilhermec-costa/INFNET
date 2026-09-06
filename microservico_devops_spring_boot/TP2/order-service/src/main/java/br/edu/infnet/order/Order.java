package br.edu.infnet.order;

public record Order(Long id, Long productId, Integer quantity, String status) {
}
