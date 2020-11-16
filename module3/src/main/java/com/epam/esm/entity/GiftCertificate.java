package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "gift_certificate")
public class GiftCertificate {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;
  @Column(nullable = false, unique = true)
  private String name;
  @Column(nullable = false, columnDefinition = "text")
  private String description;
  @Column(nullable = false, columnDefinition = "decimal(10,2)")
  private BigDecimal price;
  @Column(name = "create_date", nullable = false)
  private LocalDateTime createDate;
  @Column(name = "last_update_date", nullable = false)
  private LocalDateTime lastUpdateDate;
  @Column(nullable = false)
  private Integer duration;
  @ManyToMany(targetEntity = Tag.class, cascade = CascadeType.REMOVE)
  @JoinTable(name = "certificate_tag",
      joinColumns = @JoinColumn(name = "certificate_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
  )
  private Set<Tag> tags;
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
  private Set<Order> orders;

  public GiftCertificate(Long id, String name, String description, BigDecimal price,
      LocalDateTime createDate, LocalDateTime lastUpdateDate, Integer duration) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.createDate = createDate;
    this.lastUpdateDate = lastUpdateDate;
    this.duration = duration;
  }

  public GiftCertificate() {

  }

  public Set<Order> getOrders() {
    return orders;
  }

  public void setOrders(Set<Order> orders) {
    this.orders = orders;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GiftCertificate that = (GiftCertificate) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name) &&
        Objects.equals(description, that.description) &&
        Objects.equals(price, that.price) &&
        Objects.equals(createDate, that.createDate) &&
        Objects.equals(lastUpdateDate, that.lastUpdateDate) &&
        Objects.equals(duration, that.duration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, price, createDate, lastUpdateDate, duration);
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


  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public Set<Tag> getTags() {
    return tags;
  }

  public void setTags(Set<Tag> tags) {
    this.tags = tags;
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
}
