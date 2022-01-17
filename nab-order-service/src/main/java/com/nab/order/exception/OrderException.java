package com.nab.order.exception;

public class OrderException extends Exception {
  public OrderException(String message) {
    super(message);
  }

  public OrderException(Exception e) {
    super(e);
  }
}
