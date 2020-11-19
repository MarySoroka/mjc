package com.epam.esm.entity;

import com.epam.esm.entity.dto.GiftCertificateDTO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
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
public class GiftCertificate implements Serializable {

  private static final long serialVersionUID = 7065091900781462392L;
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  @Column(name = "certificate_id")
  private Long id;
  @Column(name="certificate_name",nullable = false, unique = true)
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
  @ManyToMany(fetch = FetchType.EAGER,targetEntity = Tag.class, cascade = CascadeType.ALL)
  @JoinTable(name = "certificate_tag",
      joinColumns = @JoinColumn(name = "certificate_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tag_id")
  )
  private Set<Tag> tags;
  @OneToMany
  @JoinColumn(name = "order_certificate_id", referencedColumnName = "certificate_id")
  private Set<Order> orders;

  public GiftCertificate(Long id, String name, String description, BigDecimal price,
      LocalDateTime createDate, LocalDateTime lastUpdateDate, Integer duration,
      Set<Tag> tags, Set<Order> orders) {
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

  public GiftCertificate(GiftCertificateDTO giftCertificate) {
    this.id = giftCertificate.getId();
    this.name = giftCertificate.getName();
    this.description = giftCertificate.getDescription();
    this.price = giftCertificate.getPrice();
    this.createDate = giftCertificate.getCreateDate();
    this.lastUpdateDate = giftCertificate.getLastUpdateDate();
    this.duration = giftCertificate.getDuration();
    this.tags = giftCertificate.getTags() != null ? giftCertificate.getTags().stream().map(Tag::new)
        .collect(Collectors.toSet()) : new HashSet<Tag>();
    this.orders =
        giftCertificate.getOrders() != null ? giftCertificate.getOrders().stream().map(Order::new)
            .collect(Collectors.toSet()) : new HashSet<Order>();
  }

  @Override
  public String toString() {
    return "GiftCertificate{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", price=" + price +
        ", createDate=" + createDate +
        ", lastUpdateDate=" + lastUpdateDate +
        ", duration=" + duration +
        ", tags=" + tags +
        ", orders=" + orders +
        '}';
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
