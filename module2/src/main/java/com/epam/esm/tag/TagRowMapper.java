package com.epam.esm.tag;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagRowMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        final Tag tag = new Tag();

        tag.setId(resultSet.getLong(TagField.ID.getFieldName()));
        tag.setTagName(resultSet.getString(TagField.TAG_NAME.getFieldName()));

        return tag;
    }
}
