package com.epam.esm.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.GiftCertificatesController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ResourceBuildException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class OrderResource extends RepresentationModel<OrderResource> {

  private static final Logger LOGGER = LoggerFactory.getLogger(OrderResource.class);
  private final Order order;

  public OrderResource(Order order) {
    try {
      this.order = order;
      final Long giftCertificateId = order.getOrderCertificateId();
      add(linkTo(OrderController.class).withRel("orders"));
      add(linkTo(methodOn(GiftCertificatesController.class).getCertificateById(giftCertificateId))
          .withRel("giftCertificate"));
      final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
      add(Link.of(uriString).withSelfRel());
      LOGGER.info("Created order from db : {}", order);
    } catch (EntityNotFoundException e) {
      LOGGER.error("Resource exception : couldn't build order : {}", order);
      throw new ResourceBuildException("Resource exception : couldn't build order");
    }
  }

  public Order getOrder() {
    return order;
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
    OrderResource that = (OrderResource) o;
    return Objects.equals(order, that.order);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), order);
  }
}
