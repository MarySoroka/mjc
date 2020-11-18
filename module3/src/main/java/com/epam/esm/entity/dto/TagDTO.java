package com.epam.esm.entity.dto;

import com.epam.esm.entity.Tag;

public class TagDTO {
  private Long tagId;

  public TagDTO() {
  }

  public TagDTO(Tag tag) {
    this.tagId = tag.getId();
    this.name = tag.getName();
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
