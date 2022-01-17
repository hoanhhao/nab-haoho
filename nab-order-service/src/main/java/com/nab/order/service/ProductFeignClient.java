package com.nab.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "${services.names.nab-product-service}")
public interface ProductFeignClient {

  @GetMapping("/product-svc/api/products/checkQuantity")
  Boolean checkQuantity(@RequestParam(name = "productId") Long productId,
      @RequestParam(name = "quantity") Integer quantity);

  @PutMapping("/product-svc/api/products/updateQuantity")
  Boolean updateQuantity(@RequestParam(name = "productId") Long productId,
      @RequestParam(name = "quantity") Integer quantity);
}
