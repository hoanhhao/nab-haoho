package com.nab.order.service;

import com.nab.order.dto.OrderDto;

import java.util.List;

public interface IProductProducer {
  void updateQuantity(List<OrderDto> orders);
}
