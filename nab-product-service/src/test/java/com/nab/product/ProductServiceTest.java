package com.nab.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nab.product.dao.ProductModel;
import com.nab.product.dao.ProductRepository;
import com.nab.product.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
  @Mock
  ProductRepository productRepository;

  @InjectMocks
  @Autowired
  private ProductService productService;

  private ProductModel model;

  @Before
  public void setUp() {
    model = new ProductModel();
    model.setName("product_name");
    model.setBrand("brand");
    model.setCategory("category");
    model.setQuantity(1);
    model.setPrice(new BigDecimal(1));
    model.setColor("red");
  }

  @Test
  public void testGetAllProducts() throws JsonProcessingException {
    List<ProductModel> products = Arrays.asList(model);

    when(productRepository.findByNameLikeAndCategoryLikeAndBrandLikeAndColorLike(
        "%" + model.getName() + "%", "%" + model.getCategory() + "%", "%" + model.getBrand() + "%",
        "%" + model.getColor() + "%")).thenReturn(products);

    List<ProductModel> returnedProducts = productService.getAllProducts(model.getName(),
        model.getCategory(), model.getBrand(), model.getColor(), model.getPrice());

    assertEquals(new ObjectMapper().writeValueAsString(returnedProducts),
        new ObjectMapper().writeValueAsString(products));
  }

  @Test
  public void testCheckQuantity() throws JsonProcessingException {
    when(productRepository.findById(model.getId())).thenReturn(Optional.of(model));

    boolean existing = productService.checkQuantity(model.getId(), model.getQuantity());

    assertEquals(true, existing);
  }

  @Test
  public void testCheckQuantityNotAvailable() throws JsonProcessingException {
    when(productRepository.findById(model.getId())).thenReturn(Optional.of(model));

    boolean existing = productService.checkQuantity(model.getId(), 2);

    assertEquals(false, existing);
  }

  @Test
  public void testUpdateQuantity() throws JsonProcessingException {
    when(productRepository.findById(model.getId())).thenReturn(Optional.of(model));

    boolean existing = productService.updateQuantity(model.getId(), 1);
    assertEquals(true, existing);
  }

  @Test
  public void testUpdateQuantityWithUnavailableProduct() throws JsonProcessingException {
    lenient().when(productRepository.findById(model.getId())).thenReturn(Optional.of(model));

    boolean existing = productService.updateQuantity(2L, 1);
    assertEquals(false, existing);
  }
}
