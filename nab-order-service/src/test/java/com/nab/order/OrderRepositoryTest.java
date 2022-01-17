// package com.nab.order;
//
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.nab.order.dao.OrderRepository;
// import com.nab.order.dto.OrderDto;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.MockitoJUnitRunner;
// import org.springframework.jdbc.core.ResultSetExtractor;
// import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
// import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//
// import java.math.BigDecimal;
// import java.util.Arrays;
// import java.util.List;
//
// import static org.junit.Assert.assertEquals;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.Mockito.doReturn;
//
// @RunWith(MockitoJUnitRunner.class)
// public class OrderRepositoryTest {
//
// @Mock
// NamedParameterJdbcTemplate namedTemplate;
//
// @InjectMocks
// private OrderRepository orderRepository;
//
// @Test
// public void testGetAll() throws JsonProcessingException {
// final String sql = "SELECT id, product_id, quantity, price, total FROM nab_db.nab_orders";
//
// OrderDto dto = new OrderDto();
// dto.setId(1L);
// dto.setQuantity(10);
// dto.setProductId(234L);
// dto.setTotal(new BigDecimal(1000));
// dto.setPrice(new BigDecimal(100));
// List<OrderDto> orders = Arrays.asList(dto);
//
// doReturn(orders)
// .when(namedTemplate)
// .query(
// eq(sql),
// any(MapSqlParameterSource.class),
// any(ResultSetExtractor.class));
//
// List<OrderDto> returnedOrders = orderRepository.getAll();
//
// assertEquals(new ObjectMapper().writeValueAsString(returnedOrders),
// new ObjectMapper().writeValueAsString(orders));
// }
// }
