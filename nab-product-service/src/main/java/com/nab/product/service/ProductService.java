package com.nab.product.service;

import com.nab.product.dao.ProductModel;
import com.nab.product.dao.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service("productService")
public class ProductService implements IProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public List<ProductModel> getAllProducts(String name, String category, String brand, String color,
      BigDecimal price) {
    return this.productRepository.findByNameLikeAndCategoryLikeAndBrandLikeAndColorLike(
        "%" + name + "%", "%" + category + "%", "%" + brand + "%", "%" + color + "%");
  }

  @Override
  public Boolean checkQuantity(Long productId, int quantity) {
    Optional<ProductModel> productModel = productRepository.findById(productId);
    if (productModel.isPresent()) {
      if (productModel.get().getQuantity() < quantity) {
        return false;
      }
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Boolean updateQuantity(Long productId, int quantity) {
    Optional<ProductModel> productModel = productRepository.findById(productId);
    if (productModel.isPresent()) {
      productModel.get().setQuantity(productModel.get().getQuantity() - quantity);
      productRepository.save(productModel.get());
      return true;
    } else {
      return false;
    }
  }
}
