package com.nab.order.controller;

import com.nab.order.dto.OrderDto;
import com.nab.order.exception.OrderException;
import com.nab.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This controller is for serving Order processes.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private final IOrderService orderService;

  public OrderController(@Qualifier("orderService") IOrderService orderService) {
    this.orderService = orderService;
  }

  /**
   * This is just testing function, just ignore it.
   *
   * @return
   */
  @Deprecated
  @GetMapping("/list")
  public ResponseEntity getAllOrders() {
    return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
  }

  /**
   * The function is to proceed Order after user has done selecting products.
   *
   * @param orders
   * @return
   * @throws Exception
   */
  @PostMapping("/proceed")
  public ResponseEntity proceedOrder(@RequestBody List<OrderDto> orders) throws Exception {
    try {
      this.orderService.proceedOrders(orders);
    } catch (OrderException ex) {
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
