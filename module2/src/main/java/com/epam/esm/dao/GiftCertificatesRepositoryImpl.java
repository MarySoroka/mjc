package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GiftCertificatesRepositoryImpl implements GiftCertificatesRepository {
    //change tag statement to certificate with group by
    private static final String INSERT_CERTIFICATES_QUERY = "INSERT INTO gift_certificates.gift_certificate (name,description, price, create_date, last_update_date, duration ) values (:name,:description,:price,:createDate,:lastUpdateDate,:duration)";
    private static final String UPDATE_CERTIFICATES_QUERY = "UPDATE gift_certificates.gift_certificate set name=:name ,description=:description, price=:price, create_date=:createDate, last_update_date=:lastUpdateDate, duration=:duration where id = :id";

    private static final String DELETE_CERTIFICATES_QUERY = "DELETE FROM gift_certificates.gift_certificate WHERE id = :id";
    private static final String SELECT_ALL_CERTIFICATES_QUERY = "SELECT id,name,description, price, create_date, last_update_date, duration FROM gift_certificates.gift_certificate";
    private static final String SELECT_CERTIFICATE_BY_ID_QUERY = "SELECT id,name,description, price, create_date, last_update_date, duration FROM gift_certificates.gift_certificate WHERE `id`= :id";
    private static final String SELECT_CERTIFICATE_BY_TAG_NAME_QUERY = "SELECT id,name,description, price, create_date, last_update_date, duration name FROM gift_certificates.gift_certificate WHERE `name`= :name";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public GiftCertificatesRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public Optional<GiftCertificate> getById(Long id) {
        Map<String, String> namedParameters = Collections.singletonMap("id", String.valueOf(id));
        List<GiftCertificate> queryForObject = this.namedParameterJdbcTemplate.query(SELECT_CERTIFICATE_BY_ID_QUERY, namedParameters, new BeanPropertyRowMapper<>(GiftCertificate.class));
        if (queryForObject.size() != 1)
            return Optional.empty();
        return Optional.ofNullable(queryForObject.get(0));
    }

    @Override
    public List<GiftCertificate> getAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_CERTIFICATES_QUERY, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public boolean delete(Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.update(DELETE_CERTIFICATES_QUERY, namedParameters) != 0;
    }


    @Override
    public Long save(GiftCertificate giftCertificate) throws DaoSaveException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(giftCertificate);
        namedParameterJdbcTemplate.update(INSERT_CERTIFICATES_QUERY, namedParameters, keyHolder, new String[]{"id"});
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new DaoSaveException("Couldn't save gift certificate");
        }
        return key.longValue();

    }

    @Override
    public boolean update(GiftCertificate giftCertificate) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(giftCertificate);
        int isUpdate = namedParameterJdbcTemplate.update(UPDATE_CERTIFICATES_QUERY, namedParameters);
        return isUpdate > 0;
    }
}
