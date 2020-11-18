package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import java.util.Map;
import java.util.Set;

public interface OrderRepository extends CRUDDao<Order, Long>{
   Set<Order> getAllUserOrders(Long userId, Integer limit, Integer offset);
}
