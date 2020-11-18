package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.dto.OrderDTO;
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
   Order createOrder(OrderDTO order) throws RepositorySaveException, EntityNotFoundException;
   void deleteOrder(Long id) throws RepositoryDeleteException;
   void updateOrder(OrderDTO order) throws RepositoryUpdateException;
   Set<Order> getAllUserOrders(Long userId, Map<String,Integer> pagination);
}
