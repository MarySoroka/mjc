package com.epam.esm.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class TagsDaoImpl implements TagsDao {
    private static final String INSERT_TAG_QUERY = "INSERT INTO gift_certificates.tag (`name`) values (?)";
    private static final String DELETE_TAG_QUERY = "DELETE FROM gift_certificates.tag WHERE id = ?";
    private static final String SELECT_ALL_TAGS_QUERY = "SELECT id, name FROM gift_certificates.tag";
    private static final String SELECT_TAG_BY_ID_QUERY = "SELECT id, name FROM gift_certificates.tag WHERE `id`=?";
    private static final String SELECT_TAG_BY_NAME_QUERY = "SELECT id, name FROM gift_certificates.tag WHERE `name`=?";
    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper tagRowMapper;

    @Autowired
    public TagsDaoImpl(JdbcTemplate jdbcTemplate, TagRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRowMapper = rowMapper;
    }


    @Override
    public Optional<Tag> getById(Long id) {
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(
                        SELECT_TAG_BY_ID_QUERY,
                        new Object[]{id},
                        tagRowMapper)
                )
        );
    }

    @Override
    public List<Tag> getAll() {
        return jdbcTemplate.query(SELECT_ALL_TAGS_QUERY, tagRowMapper);
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_TAG_QUERY, id) == 1;
    }


    @Override
    public Long save(Tag tag) throws TagSaveException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_TAG_QUERY, new String[]{"id"});
                    ps.setString(1, tag.getTagName());
                    return ps;
                },
                keyHolder);
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new TagSaveException();
        }
        return key.longValue();

    }
}
