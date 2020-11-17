package com.epam.esm.entity;

import com.epam.esm.entity.dto.OrderDTO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_order")
public class Order implements Serializable {

  private static final long serialVersionUID = -6785077151599358812L;
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  @Column(name = "order_id",nullable = false)
  private Long id;
  @Column(name = "order_certificate_id", nullable = false)
  private Long orderCertificateId;
  @Column(nullable = false)
  private LocalDateTime timestamp;
  @Column(nullable = false, columnDefinition = "decimal(10,2)")
  private BigDecimal cost;
  @Column(name = "user_id", nullable = false)
  private Long userId;


  public Order(Long id, Long orderCertificateId, LocalDateTime timestamp, BigDecimal cost,
      Long userId) {
    this.id = id;
    this.orderCertificateId = orderCertificateId;
    this.timestamp = timestamp;
    this.cost = cost;
    this.userId = userId;

  }

  public Order() {
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

  public Order(OrderDTO orderDTO) {
    this.id = orderDTO.getId();
    this.orderCertificateId = orderDTO.getOrderCertificateId();
    this.timestamp = orderDTO.getTimestamp();
    this.cost = orderDTO.getCost();
    this.userId = orderDTO.getUserId();
  }


  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
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
