package com.nab.order.service;


import com.nab.order.dto.OrderDto;

import java.util.List;

public interface IOrderService {
  List<OrderDto> getAllOrders();

  void proceedOrders(List<OrderDto> orders) throws Exception;
}
