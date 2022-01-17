package com.nab.product.controller;

import com.nab.product.service.IProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  private final IProductService productService;

  public ProductController(@Qualifier("productService") IProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/list")
  public ResponseEntity getAllProducts(@RequestParam(name = "name", required = false) String name,
      @RequestParam(value = "category", required = false) String category,
      @RequestParam(value = "brand", required = false) String brand,
      @RequestParam(value = "color", required = false) String color,
      @RequestParam(value = "price", required = false) BigDecimal price) {

    return new ResponseEntity<>(productService.getAllProducts(name, category, brand, color, price),
        HttpStatus.OK);
  }

  @GetMapping("/checkQuantity")
  public ResponseEntity checkQuantity(
      @RequestParam(name = "productId", required = false) Long productId,
      @RequestParam(name = "quantity", required = false) Integer quantity) {

    return new ResponseEntity<>(this.productService.checkQuantity(productId, quantity),
        HttpStatus.OK);
  }

  @PutMapping("/updateQuantity")
  public ResponseEntity updateQuantity(
      @RequestParam(name = "productId", required = false) Long productId,
      @RequestParam(name = "quantity", required = false) Integer quantity) {

    return new ResponseEntity<>(this.productService.updateQuantity(productId, quantity),
        HttpStatus.OK);
  }
}
