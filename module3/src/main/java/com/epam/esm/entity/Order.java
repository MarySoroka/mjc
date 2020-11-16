package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order {

  private Long id;
  private Long orderCertificateId;

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  private LocalDateTime timestamp;

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  private BigDecimal cost;
  private Long userId;

  public Order(Long id, Long orderCertificateId, LocalDateTime timestamp,
      BigDecimal cost, Long userId) {
    this.id = id;
    this.orderCertificateId = orderCertificateId;
    this.timestamp = timestamp;
    this.cost = cost;
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "Order{" +
        "id=" + id +
        ", orderCertificateId=" + orderCertificateId +
        ", timestamp=" + timestamp +
        ", cost=" + cost +
        ", userId=" + userId +
        '}';
  }

  public Order() {
  }

  public Long getOrderCertificateId() {
    return orderCertificateId;
  }

  public void setOrderCertificateId(Long orderCertificateId) {
    this.orderCertificateId = orderCertificateId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
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
        Objects.equals(cost, order.cost) &&
        Objects.equals(userId, order.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, orderCertificateId, timestamp, cost, userId);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }



  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }
}
