package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderRepository;
import com.epam.esm.entity.Order;
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
  public List<Order> getAllOrders(Map<String,Integer> pagination) {
    return orderRepository.getAll(pagination);
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
  public Order createOrder(Order order) throws RepositorySaveException, EntityNotFoundException {
    LocalDateTime currentDateTime = ServiceUtils.getCurrentDateTime();
    order.setTimestamp(currentDateTime);
    Long orderId = orderRepository.save(order);
    return getOrderById(orderId);
  }

  @Override
  public void deleteOrder(Long id) throws RepositoryDeleteException {
    orderRepository.delete(id);
  }

  @Override
  public void updateOrder(Order order) throws RepositoryUpdateException {
    orderRepository.update(order);
  }

  @Override
  public Set<Order> getAllUserOrders(Long userId,Map<String,Integer> pagination) {
    return orderRepository.getAllUserOrders(userId, pagination);
  }
}
