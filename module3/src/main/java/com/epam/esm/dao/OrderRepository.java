package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import java.util.Set;
import org.springframework.stereotype.Repository;


public interface OrderRepository extends CRUDDao<Order, Long> {

  Set<Order> getAllUserOrders(Long userId, Integer limit, Integer offset);
}
