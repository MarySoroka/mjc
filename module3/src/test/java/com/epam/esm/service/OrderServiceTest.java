package com.epam.esm.service;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.epam.esm.dao.impl.OrderRepositoryImpl;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.dto.OrderDTO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.exception.RepositoryUpdateException;
import com.epam.esm.service.impl.OrderServiceImpl;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {


  @Mock
  OrderRepositoryImpl orderRepository;

  @InjectMocks
  OrderServiceImpl orderService;
  Order order = new Order(1L, 1L, ServiceUtils.getCurrentDateTime(), new BigDecimal(13), 1L);


  @Test
  void whenMockGetAllOrdersThenReturnOrder() {
    when(orderRepository.getAll(anyInt(),anyInt())).thenReturn(Collections.singletonList(order));
    assertEquals(1L, orderService.getAllOrders(10,0).size());
  }

  @Test
  void whenMockGetExistingOrderByIdThenReturnCorrectOrder() throws EntityNotFoundException {
    when(orderRepository.getById(anyLong())).thenReturn(ofNullable(order));
    assertEquals(1L, orderService.getOrderById(1L).getId());
  }

  @Test
  void whenMockGetNotExistingOrderByIdThenThrowsException() {
    when(orderRepository.getById(anyLong())).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> orderService.getOrderById(1L));
  }

  @Test
  void whenMockCreateOrderThenDoesntThrowException() {
    when(orderRepository.save(any(Order.class))).thenReturn(order);
    assertDoesNotThrow(() -> orderService.createOrder(new OrderDTO(order)));
  }

  @Test
  void whenMockCreateIncorrectOrderThenThrowsException() {
    when(orderRepository.save(any(Order.class))).thenThrow(DataIntegrityViolationException.class);
    assertThrows(DataIntegrityViolationException.class, () -> orderService.createOrder(new OrderDTO(order)));
  }

  @Test
  void whenMockDeleteExistingOrderThenDoesntThrowException() throws RepositoryDeleteException {
    doNothing().when(orderRepository).delete(anyLong());
    assertDoesNotThrow(() -> orderService.deleteOrder(1L));

  }

  @Test
  void whenMockDeleteNotExistingOrderThenDoesntThrowException() throws RepositoryDeleteException {
    doThrow(RepositoryDeleteException.class).when(orderRepository).delete(anyLong());
    assertThrows(RepositoryDeleteException.class, () -> orderService.deleteOrder(1L));
  }

  @Test
  void whenMockUpdateExistingOrderThenDoesntThrowException() {
    doNothing().when(orderRepository).update(any(Order.class));
    assertDoesNotThrow(() -> orderService.updateOrder(new OrderDTO(order)));
  }

  @Test
  void whenMockUpdateNotExistingOrderThenDoesntThrowException() {
    doThrow(DataIntegrityViolationException.class).when(orderRepository).update(any(Order.class));
    assertThrows(DataIntegrityViolationException.class, () -> orderService.updateOrder(new OrderDTO(order)));
  }

  @Test
  void whenMockGetAllUserOrdersThenReturnOneOrder() {
    when(orderRepository.getAllUserOrders(anyLong(), anyInt(),anyInt()))
        .thenReturn(Collections.singleton(order));
    assertEquals(1L, orderService.getAllUserOrders(1L, 10,0).size());
  }
}