package com.epam.esm.mapper;

public enum TagRows {
    ID("id"),
    TAG_NAME("name");

    private final String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    TagRows(String fieldName) {
        this.fieldName = fieldName;
    }


}
