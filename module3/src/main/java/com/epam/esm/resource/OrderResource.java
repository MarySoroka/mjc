package com.epam.esm.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.GiftCertificatesController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.ControllerEntityNotFoundException;
import com.epam.esm.exception.ResourceBuildException;
import org.springframework.hateoas.RepresentationModel;

public class OrderResource extends RepresentationModel<OrderResource> {
  private final Order order;

  public OrderResource(Order order)  {
    try {
    this.order = order;
    Long giftCertificateId = order.getGiftCertificate();
    add(linkTo(OrderController.class).withRel("orders"));

      add(linkTo(methodOn(GiftCertificatesController.class).getCertificateById(giftCertificateId)).withRel("giftCertificates"));
    } catch (ControllerEntityNotFoundException e) {
      throw new ResourceBuildException("Resource exception : couldn't build order");
    }
  }
}
