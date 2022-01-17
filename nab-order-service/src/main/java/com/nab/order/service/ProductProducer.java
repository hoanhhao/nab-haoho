package com.nab.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nab.order.dto.OrderDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;

@Service
public class ProductProducer implements IProductProducer {
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final String TOPIC = "orderTopic";
  private static final Logger logger = LogManager.getLogger(IProductProducer.class);

  public ProductProducer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void updateQuantity(List<OrderDto> orders) {
    ListenableFuture<SendResult<String, String>> future = null;
    try {
      future = this.kafkaTemplate.send(TOPIC, new ObjectMapper().writeValueAsString(orders));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
      @Override
      public void onFailure(Throwable ex) {
        logger.info("Unable to send message=[ {} ]", ex.getMessage());
      }

      @Override
      public void onSuccess(SendResult<String, String> result) {
        logger.info("Sent message with offset=[ {} ]", result.getRecordMetadata().offset());
      }
    });
  }
}
