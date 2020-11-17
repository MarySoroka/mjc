package com.epam.esm.entity.dto;

public class TagDTO {
  private Long tagId;

  public TagDTO() {
  }

  public Long getTagId() {
    return tagId;
  }

  public void setTagId(Long tagId) {
    this.tagId = tagId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private String name;

}
