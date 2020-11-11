package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order {

  private Long id;
  private Long giftCertificateId;
  private LocalDateTime orderTimestamp;
  private BigDecimal purchase;
  private Long userId;

  public Order(Long id, Long giftCertificateId, LocalDateTime orderTimestamp,
      BigDecimal purchase, Long userId) {
    this.id = id;
    this.giftCertificateId = giftCertificateId;
    this.orderTimestamp = orderTimestamp;
    this.purchase = purchase;
    this.userId = userId;
  }

  public Order() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return Objects.equals(id, order.id) &&
        Objects.equals(giftCertificateId, order.giftCertificateId) &&
        Objects.equals(orderTimestamp, order.orderTimestamp) &&
        Objects.equals(purchase, order.purchase) &&
        Objects.equals(userId, order.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, giftCertificateId, orderTimestamp, purchase, userId);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getGiftCertificate() {
    return giftCertificateId;
  }

  public void setGiftCertificate(Long giftCertificateId) {
    this.giftCertificateId = giftCertificateId;
  }

  public LocalDateTime getOrderTimestamp() {
    return orderTimestamp;
  }

  public void setOrderTimestamp(LocalDateTime orderTimestamp) {
    this.orderTimestamp = orderTimestamp;
  }

  public BigDecimal getPurchase() {
    return purchase;
  }

  public void setPurchase(BigDecimal purchase) {
    this.purchase = purchase;
  }
}
