package com.nab.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDto {
  private Long id;

  private BigDecimal price;

  private BigDecimal total;

  private Integer quantity;

  private Long productId;
}
