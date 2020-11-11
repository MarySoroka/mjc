package com.epam.esm.resource;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.hateoas.RepresentationModel;

public class GiftCertificateResource extends RepresentationModel<GiftCertificateResource> {

  private final GiftCertificate giftCertificate;

  public GiftCertificateResource(GiftCertificate giftCertificate) {
    this.giftCertificate = giftCertificate;
  }
}
