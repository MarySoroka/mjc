package com.epam.esm.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagRowMapper implements RowMapper<Tag> {
    /**
     * method create tag entity from result set
     *
     * @param resultSet result set
     * @param rowNum    amount of rows
     * @return tag entity
     * @throws SQLException if we can't find such column
     */
    @Override
    public Tag mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        final Tag tag = new Tag();

        tag.setId(resultSet.getLong(TagRows.ID.getFieldName()));
        tag.setTagName(resultSet.getString(TagRows.TAG_NAME.getFieldName()));

        return tag;
    }
}
