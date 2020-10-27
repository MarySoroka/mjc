package com.epam.esm.mapper;

/**
 * enum for keeping database columns value for gift certificate table
 */
public enum GiftCertificateRows {
    ID("id"),
    NAME("name"),
    DESCRIPTION("description"),
    PRICE("price"),
    CREATE_DATE("create_date"),
    LAST_UPDATE_DATE("last_update_date"),
    DURATION("duration");

    private final String rowName;
    GiftCertificateRows(String rowName) {
        this.rowName = rowName;
    }

    public String getRowName() {
        return rowName;
    }
}
