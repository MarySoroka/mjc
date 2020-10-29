package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoSaveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class TagsRepositoryImpl implements TagsRepository {
    private static final String INSERT_TAG_QUERY = "INSERT INTO gift_certificates.tag (`name`) values (:name)";
    private static final String DELETE_TAG_QUERY = "DELETE FROM gift_certificates.tag WHERE id = :id";
    private static final String SELECT_ALL_TAGS_QUERY = "SELECT id, name FROM gift_certificates.tag";
    private static final String SELECT_TAG_BY_ID_QUERY = "SELECT id, name FROM gift_certificates.tag WHERE id= :id";
    private static final String SELECT_TAG_BY_NAME_QUERY = "SELECT id, name FROM gift_certificates.tag WHERE `name`=: name";
    private static final String SELECT_TAG_BY_CERTIFICATE_ID_QUERY = "SELECT t.id, t.name FROM gift_certificates.tag t JOIN certificate_tag ct on t.id = ct.tag_id JOIN gift_certificate gc on gc.id = ct.certificate_id WHERE gc.id =:id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public TagsRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

    }


    @Override
    public Optional<Tag> getById(Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        List<Tag> queryForObject = this.namedParameterJdbcTemplate.query(SELECT_TAG_BY_ID_QUERY, namedParameters, new BeanPropertyRowMapper<>(Tag.class));
        if (queryForObject.size()!=1){
            return Optional.empty();
        }
        return Optional.ofNullable(queryForObject.get(0));

    }

    @Override
    public List<Tag> getAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_TAGS_QUERY, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public boolean delete(Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.update(DELETE_TAG_QUERY, namedParameters) != 0;

    }

    @Override
    public boolean update(Tag tag) {
        throw new UnsupportedOperationException();
    }


    @Override
    public Long save(Tag tag) throws DaoSaveException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(tag);
        namedParameterJdbcTemplate.update(INSERT_TAG_QUERY, namedParameters, keyHolder, new String[]{"id"});
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new DaoSaveException("Couldn't save tag certificate");
        }
        return key.longValue();

    }

    @Override
    public List<Tag> getTagsByCertificateId(Long certificateId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", certificateId);
        return this.namedParameterJdbcTemplate.query(SELECT_TAG_BY_CERTIFICATE_ID_QUERY, namedParameters, new BeanPropertyRowMapper<>(Tag.class));
    }
}
