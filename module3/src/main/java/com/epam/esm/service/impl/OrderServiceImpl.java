package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderRepository;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.dto.OrderDTO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.exception.RepositoryUpdateException;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.ServiceUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  @Autowired
  public OrderServiceImpl(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  public List<Order> getAllOrders( Integer limit, Integer offset) {
    return orderRepository.getAll(limit, offset);
  }

  @Override
  public Order getOrderById(Long id) throws EntityNotFoundException {
    Optional<Order> order = orderRepository.getById(id);
    if (order.isPresent()) {
      return order.get();
    }
    throw new EntityNotFoundException("Service exception : Couldn't get order by id : " + id);
  }

  @Override
  public Order createOrder(OrderDTO order) throws RepositorySaveException {
    LocalDateTime currentDateTime = ServiceUtils.getCurrentDateTime();
    order.setTimestamp(currentDateTime);
    return orderRepository.save(new Order(order));
  }

  @Override
  public void deleteOrder(Long id) throws RepositoryDeleteException {
    orderRepository.delete(id);
  }

  @Override
  public void updateOrder(OrderDTO order) throws RepositoryUpdateException {
    orderRepository.update(new Order(order));
  }

  @Override
  public Set<Order> getAllUserOrders(Long userId, Integer limit, Integer offset) {
    return orderRepository.getAllUserOrders(userId, limit, offset);
  }
}
