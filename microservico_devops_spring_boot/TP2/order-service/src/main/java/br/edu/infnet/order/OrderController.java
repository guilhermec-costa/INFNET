package br.edu.infnet.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping
  public List<Order> findAll() {
    return orderService.findAll();
  }

  @GetMapping("/{id}")
  public Order findById(@PathVariable Long id) {
    return orderService.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Order create(@RequestBody OrderRequest request) {
    return orderService.create(request);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(OrderNotFoundException.class)
  public java.util.Map<String, String> orderNotFound(OrderNotFoundException e) {
    return java.util.Map.of("message", e.getMessage());
  }

  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  @ExceptionHandler({ ProductUnavailableException.class, IllegalArgumentException.class })
  public java.util.Map<String, String> invalidOrder(Exception e) {
    return java.util.Map.of("message", e.getMessage());
  }
}
