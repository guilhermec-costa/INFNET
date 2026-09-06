package br.edu.infnet.product;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
  private final AtomicLong sequence = new AtomicLong(2);
  private final ConcurrentHashMap<Long, Product> products = new ConcurrentHashMap<>();

  public ProductService() {
    products.put(1L, new Product(1L, "Show de Rock", "Ingresso para pista", new BigDecimal("120.00")));
    products.put(2L, new Product(2L, "Festival de Cinema", "Sessão de abertura", new BigDecimal("45.00")));
  }

  public List<Product> findAll() {
    return products.values().stream().sorted(java.util.Comparator.comparing(Product::id)).toList();
  }

  public Optional<Product> findById(Long id) {
    return Optional.ofNullable(products.get(id));
  }

  public Product create(ProductRequest request) {
    Long id = sequence.incrementAndGet();
    Product product = new Product(id, request.name(), request.description(), request.price());
    products.put(id, product);
    return product;
  }
}
