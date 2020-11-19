package com.epam.esm.controller;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.dto.OrderDTO;
import com.epam.esm.exception.ControllerSaveEntityException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.exception.RepositoryUpdateException;
import com.epam.esm.resource.OrderResource;
import com.epam.esm.service.OrderService;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

  private final OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }


  @GetMapping
  public ResponseEntity<CollectionModel<OrderResource>> getAllUserOrders(
      @RequestParam(required = false) Long userId,  @RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "10") Integer limit) {
    Set<OrderResource> userOrders;
    if (userId != null) {
      userOrders = orderService.getAllUserOrders(userId,limit, offset).stream()
          .map(OrderResource::new).collect(Collectors.toSet());
    } else {
      userOrders = orderService.getAllOrders(limit, offset).stream()
          .map(OrderResource::new).collect(Collectors.toSet());
    }
    CollectionModel<OrderResource> orderResources = CollectionModel.of(userOrders);
    final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
    orderResources.add(Link.of(uriString).withSelfRel());
    return ResponseEntity.ok(orderResources);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResource> getOrderById(@PathVariable("id") Long id) throws EntityNotFoundException {
    Order orderById = orderService.getOrderById(id);
    OrderResource orderResource = new OrderResource(orderById);
    return ResponseEntity.ok(orderResource);
  }


  @PostMapping
  public ResponseEntity<OrderResource> createOrder(
      @RequestBody OrderDTO order) throws ControllerSaveEntityException {
    try {
      final Order createdOrder = orderService.createOrder(order);
      OrderResource orderResource = new OrderResource(createdOrder);
      return ResponseEntity.ok(orderResource);
    } catch (EntityNotFoundException | RepositorySaveException e) {
      throw new ControllerSaveEntityException(
          "Controller exception: couldn't save order " + e.getMessage(), e);
    }
  }


  @PatchMapping("/{id}")
  public ResponseEntity<OrderResource> updateOrder(@PathVariable("id") Long id,
      @RequestBody OrderDTO order)
      throws RepositoryUpdateException, EntityNotFoundException {
    order.setId(id);
    orderService.updateOrder(order);
    OrderResource orderResource = new OrderResource(orderService.getOrderById(id));
    return ResponseEntity.ok(orderResource);

  }


  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) throws RepositoryDeleteException {
    orderService.deleteOrder(id);
    return ResponseEntity.noContent().build();
  }
}
