package com.nab.order.dao;

import com.nab.order.dto.OrderDto;


public interface IOrderRepository {
  void proceedOrder(OrderDto order);
}
