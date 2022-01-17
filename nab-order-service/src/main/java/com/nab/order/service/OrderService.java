package com.nab.order.service;

import com.nab.order.dao.OrderRepository;
import com.nab.order.dto.OrderDto;
import com.nab.order.exception.OrderException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service("orderService")
public class OrderService implements IOrderService {

  private final OrderRepository orderRepository;
  private final ProductFeignClient productFeignClient;
  private final IProductProducer productProducer;

  public OrderService(OrderRepository orderRepository, ProductFeignClient productFeignClient,
      IProductProducer productProducer) {
    this.orderRepository = orderRepository;
    this.productFeignClient = productFeignClient;
    this.productProducer = productProducer;
  }

  /**
   * This is just for testing, just ignore it.
   *
   * @return
   */
  @Deprecated
  @Override
  public List<OrderDto> getAllOrders() {
    return orderRepository.getAll();
  }

  /**
   * The function is to process all orders user has checked out. After order transaction is
   * complete, the service will make a call to update Products quantity
   *
   * @param orders
   * @throws OrderException
   */
  @Override
  public void proceedOrders(List<OrderDto> orders) throws OrderException {
    // validation
    for (OrderDto order : orders) {
      validateProduct(order);
    }

    // proceed order
    orders.forEach(item -> {
      this.orderRepository.proceedOrder(item);
    });

    // update quantity of products
    productProducer.updateQuantity(orders);
  }

  /**
   * Check quantity of product again to make sure number of product is right for the transcation.
   *
   * @param orderDto
   * @throws OrderException
   */
  private void validateProduct(OrderDto orderDto) throws OrderException {
    if (!productFeignClient.checkQuantity(orderDto.getProductId(), orderDto.getQuantity())) {
      throw new OrderException(
          MessageFormat.format("Product with id {0} is not correct", orderDto.getProductId()));
    }
  }
}
