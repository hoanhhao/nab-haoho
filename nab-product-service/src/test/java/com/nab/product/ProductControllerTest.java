package com.nab.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nab.product.controller.ProductController;
import com.nab.product.dao.ProductModel;
import com.nab.product.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

  @InjectMocks
  ProductController productController;

  @Mock
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

    when(productService.getAllProducts(model.getName(), model.getCategory(), model.getBrand(),
        model.getColor(), null)).thenReturn(products);

    ResponseEntity returnedProducts = productController.getAllProducts(model.getName(),
        model.getCategory(), model.getBrand(), model.getColor(), null);

    assertEquals(new ObjectMapper().writeValueAsString(returnedProducts.getBody()),
        new ObjectMapper().writeValueAsString(products));
    assertEquals(HttpStatus.OK, returnedProducts.getStatusCode());
  }

  @Test
  public void testCheckQuantity() {
    when(productService.checkQuantity(model.getId(), model.getQuantity())).thenReturn(true);

    ResponseEntity returnedResults =
        productController.checkQuantity(model.getId(), model.getQuantity());

    assertEquals(true, returnedResults.getBody());
    assertEquals(HttpStatus.OK, returnedResults.getStatusCode());
  }

  @Test
  public void testCheckQuantityWithUnavailableProducts() {
    when(productService.checkQuantity(model.getId(), model.getQuantity())).thenReturn(false);

    ResponseEntity returnedResults =
        productController.checkQuantity(model.getId(), model.getQuantity());

    assertEquals(false, returnedResults.getBody());
    assertEquals(HttpStatus.OK, returnedResults.getStatusCode());
  }

  @Test
  public void testUpdateQuantity() {
    when(productService.updateQuantity(model.getId(), model.getQuantity())).thenReturn(true);

    ResponseEntity returnedResults =
        productController.updateQuantity(model.getId(), model.getQuantity());

    assertEquals(true, returnedResults.getBody());
    assertEquals(HttpStatus.OK, returnedResults.getStatusCode());
  }

  @Test
  public void testUpdateQuantityWithUnavailableProducts() {
    when(productService.updateQuantity(model.getId(), model.getQuantity())).thenReturn(false);

    ResponseEntity returnedResults =
        productController.updateQuantity(model.getId(), model.getQuantity());

    assertEquals(false, returnedResults.getBody());
    assertEquals(HttpStatus.OK, returnedResults.getStatusCode());
  }
}
