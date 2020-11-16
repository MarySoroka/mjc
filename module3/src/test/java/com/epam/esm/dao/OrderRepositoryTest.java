package com.epam.esm.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.esm.dao.impl.OrderRepositoryImpl;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.service.ServiceUtils;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

class OrderRepositoryTest {

  private static final Map<String, Integer> pagination = new HashMap<String, Integer>(2);
  private static OrderRepository orderRepository;
  private final Order order = new Order(2L, 3L, ServiceUtils.getCurrentDateTime(),
      new BigDecimal(12),
      2L);

  @BeforeAll
  public static void setup() {
    DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
        .generateUniqueName(true)
        .addScript("classpath:database.sql")
        .addScript("classpath:test-data.sql")
        .build();
    NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    orderRepository = new OrderRepositoryImpl(jdbcTemplate);
    pagination.put("limit", 10);
    pagination.put("offset", 0);
  }

  @Test
  void whenGetAllUserOrdersThenReturnTwoOrders() {
    Set<Order> allUserOrders = orderRepository.getAllUserOrders(1L, pagination);
    assertEquals(1L, allUserOrders.size());
  }

  @Test
  void whenGetAllUserOrdersIfUserDoesntExistThenReturnEmptySet() {
    Set<Order> allUserOrders = orderRepository.getAllUserOrders(100L, pagination);
    assertEquals(0L, allUserOrders.size());
  }

  @Test
  void whenGetByIdExistingOrderThenReturnCorrectOrder() {
    Optional<Order> orderOptional = orderRepository.getById(3L);
    assertTrue(orderOptional.isPresent());
    Order order = orderOptional.get();
    assertEquals(3L, order.getId());
  }

  @Test
  void whenGetByIdNotExistingOrderThenReturnCorrectOrder() {
    Optional<Order> orderOptional = orderRepository.getById(100L);
    assertFalse(orderOptional.isPresent());
  }

  @Test
  void whenGetAllOrdersThenReturnThreeOrders() {
    List<Order> orders = orderRepository.getAll(pagination);
    assertEquals(4L, orders.size());
  }

  @Test
  void whenSaveOrderThenDoesntThrowException() {
    assertDoesNotThrow(() -> orderRepository.save(order));
  }

  @Test
  void whenUpdateOrderThenDoesntThrowException() {
    assertDoesNotThrow(() -> orderRepository.update(order));
  }

  @Test
  void whenDeleteExistingOrderThenDoesntThrowException() {
    assertDoesNotThrow(() -> orderRepository.delete(1L));
  }

  @Test
  void whenDeleteNotExistingOrderThenThrowsException() {
    assertThrows(RepositoryDeleteException.class, () -> orderRepository.delete(1000L));
  }
}