package com.epam.esm.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.UserController;
import com.epam.esm.entity.User;
import org.springframework.hateoas.RepresentationModel;

public class UserResource extends RepresentationModel<UserResource> {
  private final User user;


  public UserResource(final User user) {
    this.user = user;
    final long id = user.getId();
    add(linkTo(UserController.class).withRel("user"));
    add(linkTo(methodOn(OrderController.class).getAllUserOrders(id)).withRel("orders"));

  }
}
