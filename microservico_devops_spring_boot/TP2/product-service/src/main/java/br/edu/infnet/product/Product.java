package br.edu.infnet.product;

import java.math.BigDecimal;

public record Product(Long id, String name, String description, BigDecimal price) {
}
