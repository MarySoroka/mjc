package com.epam.esm.tag;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagRowMapper implements RowMapper<Tag> {

    public TagRowMapper() {
    }

    @Override
    public Tag mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        final Tag tag = new Tag();

        tag.setId(resultSet.getLong(TagRows.ID.getFieldName()));
        tag.setTagName(resultSet.getString(TagRows.TAG_NAME.getFieldName()));

        return tag;
    }
}
