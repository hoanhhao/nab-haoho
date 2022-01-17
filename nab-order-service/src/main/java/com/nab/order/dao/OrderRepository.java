package com.nab.order.dao;

import com.nab.order.dto.OrderDto;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class OrderRepository implements IOrderRepository {
  private final NamedParameterJdbcTemplate namedTemplate;

  public OrderRepository(NamedParameterJdbcTemplate namedTemplate) {
    this.namedTemplate = namedTemplate;
  }

  @Override
  public void proceedOrder(OrderDto orderDto) {
    final String sql = "INSERT INTO `nab_orders` (product_id, quantity, price, total) "
        + "VALUES (:productId, :quantity, :price, :total)                ";
    final MapSqlParameterSource paramsMap = new MapSqlParameterSource();
    paramsMap.addValue("productId", orderDto.getProductId());
    paramsMap.addValue("quantity", orderDto.getQuantity());
    paramsMap.addValue("price", orderDto.getPrice());
    paramsMap.addValue("total", orderDto.getTotal());

    namedTemplate.update(sql, paramsMap);
  }

  public List<OrderDto> getAll() {
    final String sql = "SELECT id, product_id, quantity, price, total FROM nab_db.nab_orders";
    final MapSqlParameterSource paramsMap = new MapSqlParameterSource();

    List<OrderDto> listResult = namedTemplate.query(sql, paramsMap, (rs, rowNum) -> {
      OrderDto presenter = new OrderDto();
      presenter.setId(rs.getLong("id"));
      presenter.setProductId(rs.getLong("product_id"));
      presenter.setQuantity(rs.getInt("quantity"));
      presenter.setPrice(rs.getBigDecimal("price"));
      presenter.setTotal(rs.getBigDecimal("total"));
      return presenter;
    });
    return listResult;
  }
}
