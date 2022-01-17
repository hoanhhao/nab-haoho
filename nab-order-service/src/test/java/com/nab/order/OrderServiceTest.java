package com.nab.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nab.order.dao.OrderRepository;
import com.nab.order.dto.OrderDto;
import com.nab.order.exception.OrderException;
import com.nab.order.service.OrderService;
import com.nab.order.service.ProductFeignClient;
import com.nab.order.service.ProductProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
  @Mock
  OrderRepository orderRepository;

  @Mock
  ProductProducer productProducer;

  @Mock
  ProductFeignClient productFeignClient;

  @InjectMocks
  @Autowired
  private OrderService orderService;

  @Test
  public void testGetAllOrders() throws JsonProcessingException {
    OrderDto dto = new OrderDto();
    dto.setId(1L);
    dto.setQuantity(10);
    dto.setProductId(234L);
    dto.setTotal(new BigDecimal(1000));
    dto.setPrice(new BigDecimal(100));
    List<OrderDto> orders = Arrays.asList(dto);

    when(orderRepository.getAll()).thenReturn(orders);

    List<OrderDto> returnedOrders = orderService.getAllOrders();

    assertEquals(new ObjectMapper().writeValueAsString(returnedOrders),
        new ObjectMapper().writeValueAsString(orders));
  }

  @Test
  public void testProceedOrders() throws OrderException {

    OrderDto dto = new OrderDto();
    dto.setId(1L);
    dto.setQuantity(10);
    dto.setProductId(234L);
    dto.setTotal(new BigDecimal(1000));
    dto.setPrice(new BigDecimal(100));
    List<OrderDto> orders = Arrays.asList(dto);

    doNothing().when(productProducer).updateQuantity(orders);
    doNothing().when(orderRepository).proceedOrder(orders.get(0));

    when(
        productFeignClient.checkQuantity(orders.get(0).getProductId(), orders.get(0).getQuantity()))
            .thenReturn(true);

    orderService.proceedOrders(orders);
  }

  @Test(expected = OrderException.class)
  public void testProceedOrdersWithValidationFail() throws OrderException {

    OrderDto dto = new OrderDto();
    dto.setId(1L);
    dto.setQuantity(10);
    dto.setProductId(234L);
    dto.setTotal(new BigDecimal(1000));
    dto.setPrice(new BigDecimal(100));
    List<OrderDto> orders = Arrays.asList(dto);

    doNothing().when(productProducer).updateQuantity(orders);
    doNothing().when(orderRepository).proceedOrder(orders.get(0));

    when(
        productFeignClient.checkQuantity(orders.get(0).getProductId(), orders.get(0).getQuantity()))
            .thenReturn(false);

    orderService.proceedOrders(orders);
  }
}
