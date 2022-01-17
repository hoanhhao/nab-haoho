package com.nab.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nab.product.dto.OrderDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderConsumer {
  private static final Logger logger = LogManager.getLogger(OrderConsumer.class);
  private final IProductService productService;

  public OrderConsumer(IProductService productService) {
    this.productService = productService;
  }

  @KafkaListener(topics = "orderTopic", groupId = "nab_group_id")
  public void consume(String message) {
    try {
      List<OrderDto> orders =
          new ObjectMapper().readValue(message, new TypeReference<List<OrderDto>>() {});
      logger.info("Request " + orders);
      orders.forEach(item -> {
        this.productService.updateQuantity(item.getProductId(), item.getQuantity());
      });
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    logger.info("$$$$ => Consumed message: \n{}");
  }
}
