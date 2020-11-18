package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.exception.RepositoryUpdateException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OrderService {
   List<Order> getAllOrders(Integer limit, Integer offset);
   Order getOrderById(Long id) throws EntityNotFoundException;
   Order createOrder(Order order) throws RepositorySaveException, EntityNotFoundException;
   void deleteOrder(Long id) throws RepositoryDeleteException;
   void updateOrder(Order order) throws RepositoryUpdateException;
   Set<Order> getAllUserOrders(Long userId, Integer limit, Integer offset);
}
