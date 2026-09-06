package br.edu.infnet.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductClient {
  private final RestClient restClient;

  public ProductClient(@Value("${product-service.url}") String productServiceUrl) {
    this.restClient = RestClient.builder().baseUrl(productServiceUrl).build();
  }

  public boolean exists(Long productId) {
    try {
      return restClient.get().uri("/products/{id}", productId).retrieve()
          .onStatus(HttpStatusCode::isError, (req, res) -> {
          }).toBodilessEntity().getStatusCode().is2xxSuccessful();
    } catch (Exception ex) {
      return false;
    }
  }
}
