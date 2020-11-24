package com.epam.esm.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.UserController;
import com.epam.esm.entity.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class UserResource extends RepresentationModel<UserResource> {

  private final User user;

  public UserResource(final User user) {
    this.user = user;
    final long id = user.getId();
    add(linkTo(UserController.class).withRel("users"));
    add(linkTo(methodOn(OrderController.class).getAllUserOrders(id, 10,0)).withRel("orders"));
    final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
    add(Link.of(uriString).withSelfRel());
  }

  public User getUser() {
    return user;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    UserResource that = (UserResource) o;
    return Objects.equals(user, that.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), user);
  }
}
