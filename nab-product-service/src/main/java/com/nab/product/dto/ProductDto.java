package com.nab.product.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
  private String name;

  private String category;

  private String brand;

  private String color;

  private BigDecimal price;

  private Integer quantity;
}
