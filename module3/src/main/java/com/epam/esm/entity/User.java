package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User implements Serializable {

  private static final long serialVersionUID = -5597587150308014843L;
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  @Column(name = "user_id")
  private Long id;
  @Column(name = "user_name",nullable = false)
  private String name;
  @Column(nullable = false)
  private String surname;
  @JsonIgnore
  @OneToMany(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private Set<Order> orders;

  public User(Long id, String name, String surname) {
    this.id = id;
    this.name = name;
    this.surname = surname;
  }

  public User() {
  }

  public User(String name, String surname) {
    this.name = name;
    this.surname = surname;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", surname='" + surname + '\'' +
        ", orders=" + orders +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(id, user.id) &&
        Objects.equals(name, user.name) &&
        Objects.equals(surname, user.surname) &&
        Objects.equals(orders, user.orders);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, surname, orders);
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

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public Set<Order> getOrders() {
    return orders;
  }

  public void setOrders(Set<Order> orders) {
    this.orders = orders;
  }

}
