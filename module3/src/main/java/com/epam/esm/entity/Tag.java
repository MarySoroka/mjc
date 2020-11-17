package com.epam.esm.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tag")
public class Tag  implements Serializable {

  private static final long serialVersionUID = 7060096489786514736L;
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  @Column(name = "tag_id")
  private Long id;
  @Column(nullable = false, unique = true)
  private String name;

  public Tag() {
  }

  @Override
  public String


  toString() {
    return "Tag{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }

  public Tag(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Tag(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tag tag = (Tag) o;
    return Objects.equals(name, tag.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
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
}
