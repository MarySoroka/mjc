package com.epam.esm.entity.dto;

import com.epam.esm.entity.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDTO {

  private Long id;
  private Long orderCertificateId;
  private LocalDateTime timestamp;
  private BigDecimal cost;
  private Long userId;

  public OrderDTO(Long id, Long orderCertificateId, LocalDateTime timestamp,
      BigDecimal cost, Long userId) {
    this.id = id;
    this.orderCertificateId = orderCertificateId;
    this.timestamp = timestamp;
    this.cost = cost;
    this.userId = userId;
  }

  public OrderDTO() {
  }
  public OrderDTO(Order order) {
    this.id = order.getId();
    this.orderCertificateId = order.getOrderCertificateId();
    this.timestamp = order.getTimestamp();
    this.cost = order.getCost();
    this.userId = order.getUserId();
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

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
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
}
