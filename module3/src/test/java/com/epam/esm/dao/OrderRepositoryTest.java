package com.epam.esm.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.service.ServiceUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class OrderRepositoryTest {

  private final Order order = new Order(null, 1L, ServiceUtils.getCurrentDateTime(), new BigDecimal(12), 1L);
  private final Order order2 = new Order(null, 2L, ServiceUtils.getCurrentDateTime(), new BigDecimal(13), 2L);

  private final GiftCertificate giftCertificate = new GiftCertificate(null, "name3", "desk", new BigDecimal(12),
      ServiceUtils.getCurrentDateTime(), ServiceUtils.getCurrentDateTime(), 13);
  private final GiftCertificate giftCertificate2 = new GiftCertificate(null, "name4", "desk", new BigDecimal(12),
      ServiceUtils.getCurrentDateTime(), ServiceUtils.getCurrentDateTime(), 13);

  private final User user = new User(null, "Mary", "D");
  private final User user2 = new User(null, "Mary", "D");

  @Autowired
  private EntityManager entityManager;
  @Autowired
  private OrderRepository orderRepository;

  @Test
  @Transactional
  void whenGetAllUserOrdersThenReturnOneOrders() throws RepositorySaveException {

    entityManager.persist(user2);
    entityManager.persist(giftCertificate2);
    order2.setUserId(user2.getId());
    order2.setOrderCertificateId(giftCertificate2.getId());
    orderRepository.save(order2);
    Set<Order> allUserOrders = orderRepository.getAllUserOrders(user2.getId(), 10, 0);
    assertEquals(1L, allUserOrders.size());
  }

  @Test
  void whenGetAllUserOrdersIfUserDoesntExistThenReturnEmptySet() {
    Set<Order> allUserOrders = orderRepository.getAllUserOrders(1L, 10, 0);
    assertEquals(0L, allUserOrders.size());
  }

  @Test
  @Transactional
  void whenGetByIdExistingOrderThenReturnCorrectOrder() throws RepositorySaveException {
    entityManager.persist(user);
    entityManager.persist(giftCertificate);
    entityManager.persist(order);
    orderRepository.save(order);
    Long id = order.getId();
    Optional<Order> orderOptional = orderRepository.getById(id);
    assertTrue(orderOptional.isPresent());
    Order order = orderOptional.get();
    assertEquals(id, order.getId());
  }

  @Test
  @Transactional
  void whenGetByIdNotExistingOrderThenReturnCorrectOrder() {
    entityManager.persist(user);
    entityManager.persist(giftCertificate);
    entityManager.persist(order);
    Optional<Order> orderOptional = orderRepository.getById(100L);
    assertFalse(orderOptional.isPresent());
  }

  @Test
  void whenGetAllOrdersThenReturnNoOrders() {
    List<Order> orders = orderRepository.getAll(10, 0);
    assertEquals(0L, orders.size());
  }

  @Test
  @Transactional
  void whenSaveOrderThenDoesntThrowException() {
    entityManager.persist(user);
    entityManager.persist(giftCertificate);
    assertDoesNotThrow(() -> orderRepository.save(order));
  }

  @Test
  @Transactional
  void whenUpdateOrderThenDoesntThrowException() {
    entityManager.persist(user);
    entityManager.persist(giftCertificate);
    entityManager.persist(order);
    order.setCost(new BigDecimal(100));
    assertDoesNotThrow(() -> orderRepository.update(order));
  }

  @Test
  @Transactional
  void whenDeleteExistingOrderThenDoesntThrowException() {
    entityManager.persist(user);
    entityManager.persist(giftCertificate);
    entityManager.persist(order);
    assertDoesNotThrow(() -> orderRepository.delete(order.getId()));
  }

  @Test
  void whenDeleteNotExistingOrderThenThrowsException() {
    assertThrows(RepositoryDeleteException.class, () -> orderRepository.delete(1000L));
  }

}