package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderRepository;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.exception.RepositoryUpdateException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

  private static final String INSERT_ORDERS_QUERY = "INSERT INTO gift_certificates.order (user_id, timestamp, purchase, order_certificate_id) values (:userId,:orderTimestamp,:purchase,:giftCertificateId)";
  private static final String UPDATE_ORDERS_QUERY = "UPDATE gift_certificates.order set user_id=:userId, timestamp=:orderTimestamp, purchase=:purchase, order_certificate_id=:giftCertificateId where id = :id";
  private static final String DELETE_ORDER_QUERY = "DELETE FROM gift_certificates.`order` WHERE id = :id";
  private static final String SELECT_ALL_ORDERS_QUERY = "SELECT id, user_id, timestamp, purchase, orderCertificateId  FROM gift_certificates.`order`";
  private static final String SELECT_ORDER_BY_ID_QUERY = "SELECT id, user_id, timestamp, purchase, orderCertificateId  FROM gift_certificates.`order`  WHERE id = :id";
  private static final String SELECT_ORDER_BY_USER_ID_QUERY = "SELECT id, user_id, timestamp, purchase, orderCertificateId  FROM gift_certificates.`order`  WHERE user_id = :userId";
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  public OrderRepositoryImpl(
      NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public Optional<Order> getById(Long id) {
    SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
    List<Order> queryForObject = this.namedParameterJdbcTemplate
        .query(SELECT_ORDER_BY_ID_QUERY, namedParameters,
            new BeanPropertyRowMapper<>(Order.class));
    if (queryForObject.size() != 1) {
      return Optional.empty();
    }
    return Optional.ofNullable(queryForObject.get(0));
  }

  @Override
  public List<Order> getAll() {
    return namedParameterJdbcTemplate
        .query(SELECT_ALL_ORDERS_QUERY, new BeanPropertyRowMapper<>(Order.class));

  }

  @Override
  public void delete(Long id) throws RepositoryDeleteException {
    SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
    if (namedParameterJdbcTemplate.update(DELETE_ORDER_QUERY, namedParameters) == 0) {
      throw new RepositoryDeleteException(
          "Repository exception: Couldn't delete order entity with id : " + id);
    }
  }

  @Override
  public void update(Order order) throws RepositoryUpdateException {
    SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(order);
    int isUpdate = namedParameterJdbcTemplate.update(UPDATE_ORDERS_QUERY, namedParameters);
    if (isUpdate == 0) {
      throw new RepositoryUpdateException(
          "Repository exception: Couldn't update order with id : " + order
              .getId());
    }
  }

  @Override
  public Long save(Order order) throws RepositorySaveException {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(order);
    namedParameterJdbcTemplate
        .update(INSERT_ORDERS_QUERY, namedParameters, keyHolder, new String[]{"id"});
    Number key = keyHolder.getKey();
    if (key == null) {
      throw new RepositorySaveException("Repository exception: Couldn't save order");
    }
    return key.longValue();
  }

  @Override
  public Set<Order> getAllUserOrders(Long userId) {
    SqlParameterSource namedParameters = new MapSqlParameterSource("userId", userId);
    return new HashSet<>(this.namedParameterJdbcTemplate
        .query(SELECT_ORDER_BY_USER_ID_QUERY, namedParameters,
            new BeanPropertyRowMapper<>(Order.class)));
  }
}
