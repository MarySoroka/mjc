package com.epam.esm.resource;

import com.epam.esm.entity.GiftCertificate;
import java.util.Objects;
import org.springframework.hateoas.RepresentationModel;

public class GiftCertificateResource extends RepresentationModel<GiftCertificateResource> {

  private final GiftCertificate giftCertificate;

  public GiftCertificateResource(GiftCertificate giftCertificate) {
    this.giftCertificate = giftCertificate;
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