package com.epam.esm.entity;

import java.util.Optional;
import java.util.stream.Stream;

public enum GiftCertificateFields {
  ID("id"),
  NAME("name"),
  DESCRIPTION("description"),
  CREATE_DATE("createDate"),
  LAST_UPDATE_DATE("lastUpdateDate"),
  DURATION("duration"),
  PRICE("price");

  private final String field;

  GiftCertificateFields(String field) {

    this.field = field;
  }

  public static Optional<GiftCertificateFields> of(String name) {
    return Stream.of(GiftCertificateFields.values()).filter(t -> t.field.equalsIgnoreCase(name))
        .findFirst();
  }

  public String getField() {
    return field;
  }
}
