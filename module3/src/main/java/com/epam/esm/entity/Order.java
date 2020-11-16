package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;
  @Column(name = "order_certificate_id", nullable = false)
  private Long orderCertificateId;

  @Column(nullable = false)
  private LocalDateTime timestamp;


  @Column(nullable = false, columnDefinition = "decimal(10,2)")
  private BigDecimal cost;
  @Column(name = "user_id", nullable = false)
  private Long userId;
  private GiftCertificate giftCertificate;

  public Order(Long id, Long orderCertificateId, LocalDateTime timestamp, BigDecimal cost,
      Long userId, GiftCertificate giftCertificate) {
    this.id = id;
    this.orderCertificateId = orderCertificateId;
    this.timestamp = timestamp;
    this.cost = cost;
    this.userId = userId;
    this.giftCertificate = giftCertificate;
  }

  public Order(Long id, Long orderCertificateId, LocalDateTime timestamp,
      BigDecimal cost, Long userId) {
    this.id = id;
    this.orderCertificateId = orderCertificateId;
    this.timestamp = timestamp;
    this.cost = cost;
    this.userId = userId;
  }

  public Order() {
  }

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "order_certificate_id", nullable = false)
  public GiftCertificate getGiftCertificate() {
    return giftCertificate;
  }

  public void setGiftCertificate(GiftCertificate giftCertificate) {
    this.giftCertificate = giftCertificate;
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
