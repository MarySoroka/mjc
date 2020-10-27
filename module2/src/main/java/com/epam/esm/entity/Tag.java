package com.epam.esm.entity;

public class Tag {
    private Long id;
    private String tagName;

    public Long getId() {
        return id;
    }

    public Tag() {
    }

    public Tag(Long id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
