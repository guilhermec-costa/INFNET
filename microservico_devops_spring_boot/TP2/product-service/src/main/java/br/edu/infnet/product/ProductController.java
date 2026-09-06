package br.edu.infnet.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public List<Product> findAll() {
    return productService.findAll();
  }

  @GetMapping("/{id}")
  public Product findById(@PathVariable Long id) {
    return productService.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Product create(@RequestBody ProductRequest request) {
    return productService.create(request);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ProductNotFoundException.class)
  public java.util.Map<String, String> handleNotFound(ProductNotFoundException e) {
    return java.util.Map.of("message", e.getMessage());
  }
}
