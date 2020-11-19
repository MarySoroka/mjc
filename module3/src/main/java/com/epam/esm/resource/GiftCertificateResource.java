package com.epam.esm.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.GiftCertificatesController;
import com.epam.esm.controller.TagsController;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ResourceBuildException;
import java.util.Objects;
import org.springframework.hateoas.RepresentationModel;

public class GiftCertificateResource extends RepresentationModel<GiftCertificateResource> {

  private final GiftCertificate giftCertificate;

  public GiftCertificateResource(GiftCertificate giftCertificate) {
    try {
      this.giftCertificate = giftCertificate;
      add(linkTo(GiftCertificatesController.class).withRel("certificates"));
      for (Tag tag : giftCertificate.getTags()) {
        add(linkTo(methodOn(TagsController.class).getTagById(tag.getId())).withRel("tag"));
      }
    } catch (EntityNotFoundException e) {
      throw new ResourceBuildException("Resource exception: couldn't build certificate ", e);
    }
  }

  public GiftCertificate getGiftCertificate() {
    return giftCertificate;
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
    GiftCertificateResource that = (GiftCertificateResource) o;
    return Objects.equals(giftCertificate, that.giftCertificate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), giftCertificate);
  }
}
