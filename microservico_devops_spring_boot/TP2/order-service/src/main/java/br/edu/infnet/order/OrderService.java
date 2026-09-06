package br.edu.infnet.order;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {
  private final ProductClient productClient;
  private final AtomicLong sequence = new AtomicLong();
  private final Map<Long, Order> orders = new ConcurrentHashMap<>();

  public OrderService(ProductClient productClient) {
    this.productClient = productClient;
  }

  public List<Order> findAll() {
    return orders.values().stream().sorted(Comparator.comparing(Order::id)).toList();
  }

  public Optional<Order> findById(Long id) {
    return Optional.ofNullable(orders.get(id));
  }

  public Order create(OrderRequest request) {
    if (request.productId() == null || !productClient.exists(request.productId()))
      throw new ProductUnavailableException(request.productId());
    if (request.quantity() == null || request.quantity() < 1)
      throw new IllegalArgumentException("A quantidade deve ser maior que zero");
    Long id = sequence.incrementAndGet();
    Order order = new Order(id, request.productId(), request.quantity(), "CRIADO");
    orders.put(id, order);
    return order;
  }
}
