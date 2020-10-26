package com.epam.esm.tag;

public enum TagField {
    ID("id"),
    TAG_NAME("name");

    private final String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    TagField(String fieldName) {
        this.fieldName = fieldName;
    }


}
