package com.epam.esm.mapper;
/**
 * enum for keeping database columns value for tag table
 */
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
