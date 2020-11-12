package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order {

  private Long id;
  private Long orderCertificateId;
  private LocalDateTime timestamp;
  private BigDecimal purchase;
  private Long userId;

  public Order(Long id, Long orderCertificateId, LocalDateTime timestamp,
      BigDecimal purchase, Long userId) {
    this.id = id;
    this.orderCertificateId = orderCertificateId;
    this.timestamp = timestamp;
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
        Objects.equals(orderCertificateId, order.orderCertificateId) &&
        Objects.equals(timestamp, order.timestamp) &&
        Objects.equals(purchase, order.purchase) &&
        Objects.equals(userId, order.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, orderCertificateId, timestamp, purchase, userId);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getGiftCertificate() {
    return orderCertificateId;
  }

  public void setGiftCertificate(Long giftCertificateId) {
    this.orderCertificateId = giftCertificateId;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public BigDecimal getPurchase() {
    return purchase;
  }

  public void setPurchase(BigDecimal purchase) {
    this.purchase = purchase;
  }
}
