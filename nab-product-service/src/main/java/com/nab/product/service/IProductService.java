package com.nab.product.service;


import com.nab.product.dao.ProductModel;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {
  List<ProductModel> getAllProducts(String name, String category, String brand, String color,
      BigDecimal price);

  Boolean checkQuantity(Long productId, int quantity);

  Boolean updateQuantity(Long productId, int quantity);
}
