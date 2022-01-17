package com.nab.product.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
  List<ProductModel> findByNameLikeAndCategoryLikeAndBrandLikeAndColorLike(String name,
      String category, String brand, String color);
}
