package com.epam.esm.entity.dto;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GiftCertificateDTO {

  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private LocalDateTime createDate;
  private LocalDateTime lastUpdateDate;
  private Integer duration;
  private Set<TagDTO> tags;
  private Set<OrderDTO> orders;

  public GiftCertificateDTO(Long id, String name, String description, BigDecimal price,
      LocalDateTime createDate, LocalDateTime lastUpdateDate, Integer duration,
      Set<TagDTO> tags, Set<OrderDTO> orders) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.createDate = createDate;
    this.lastUpdateDate = lastUpdateDate;
    this.duration = duration;
    this.tags = tags;
    this.orders = orders;
  }

  public GiftCertificateDTO(GiftCertificate giftCertificate) {
    this.id = giftCertificate.getId();
    this.name = giftCertificate.getName();
    this.description = giftCertificate.getDescription();
    this.price = giftCertificate.getPrice();
    this.createDate = giftCertificate.getCreateDate();
    this.lastUpdateDate = giftCertificate.getLastUpdateDate();
    this.duration = giftCertificate.getDuration();
    this.tags = giftCertificate.getTags() != null ? giftCertificate.getTags().stream().map(TagDTO::new)
        .collect(Collectors.toSet()) : new HashSet<TagDTO>();
    this.orders =
        giftCertificate.getOrders() != null ? giftCertificate.getOrders().stream().map(OrderDTO::new)
            .collect(Collectors.toSet()) : new HashSet<OrderDTO>();

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GiftCertificateDTO that = (GiftCertificateDTO) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name) &&
        Objects.equals(description, that.description) &&
        Objects.equals(price, that.price) &&
        Objects.equals(createDate, that.createDate) &&
        Objects.equals(lastUpdateDate, that.lastUpdateDate) &&
        Objects.equals(duration, that.duration) &&
        Objects.equals(tags, that.tags) &&
        Objects.equals(orders, that.orders);
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(id, name, description, price, createDate, lastUpdateDate, duration, tags, orders);
  }

  public GiftCertificateDTO() {
  }

  public Set<OrderDTO> getOrders() {
    return orders;
  }

  public void setOrders(Set<OrderDTO> orders) {
    this.orders = orders;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  public LocalDateTime getLastUpdateDate() {
    return lastUpdateDate;
  }

  public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
    this.lastUpdateDate = lastUpdateDate;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public Set<TagDTO> getTags() {
    return tags;
  }

  public void setTags(Set<TagDTO> tags) {
    this.tags = tags;
  }
}
