package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.exception.RepositoryUpdateException;
import java.util.List;

public interface OrderService {
   List<Order> getAllOrders();
   Order getOrderById(Long id) throws EntityNotFoundException;
   void createOrder(Order order) throws RepositorySaveException;
   void deleteOrder(Long id) throws RepositoryDeleteException;
   void updateOrder(Order order) throws RepositoryUpdateException;

}
