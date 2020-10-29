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
    // TODO: 29.10.2020 update certificate creation(add tag creation in certificate_tag and checking in tag) 
    // TODO: 29.10.2020 update tests
    // TODO: 29.10.2020 add date convert
    // TODO: 29.10.2020 add exception handling
    // TODO: 29.10.2020 add server response for void in controller
    // TODO: 29.10.2020 update tests due to requirements
    private static final String INSERT_CERTIFICATES_QUERY = "INSERT INTO gift_certificates.gift_certificate (name,description, price, create_date, last_update_date, duration ) values (:name,:description,:price,:createDate,:lastUpdateDate,:duration)";
    private static final String INSERT_CERTIFICATE_TAG_QUERY = "INSERT INTO gift_certificates.certificate_tag (tag_id, certificate_id) values (:tagId,:certificateId)";

    private static final String UPDATE_CERTIFICATES_QUERY = "UPDATE gift_certificates.gift_certificate set name=:name ,description=:description, price=:price, create_date=:createDate, last_update_date=:lastUpdateDate, duration=:duration where id = :id";

    private static final String DELETE_CERTIFICATES_QUERY = "DELETE FROM gift_certificates.gift_certificate WHERE id = :id";
    private static final String SELECT_ALL_CERTIFICATES_QUERY = "SELECT gc.id,gc.name,gc.description, gc.price, gc.create_date, gc.last_update_date, gc.duration FROM gift_certificates.gift_certificate gc JOIN certificate_tag ct on gc.id = ct.certificate_id JOIN tag t on t.id = ct.tag_id";
    private static final String SELECT_CERTIFICATE_BY_ID_QUERY = "SELECT gc.id,gc.name,gc.description, gc.price, gc.create_date, gc.last_update_date, gc.duration FROM gift_certificates.gift_certificate gc  WHERE gc.id = :id";
    private static final String SELECT_CERTIFICATE_BY_TAG_NAME_QUERY = "SELECT gc.id,gc.name,gc.description, gc.price, gc.create_date, gc.last_update_date, gc.duration FROM gift_certificates.gift_certificate gc JOIN certificate_tag ct on gc.id = ct.certificate_id JOIN tag t on t.id = ct.tag_id WHERE t.name= :name";

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

    @Override
    public List<GiftCertificate> getGiftCertificatesByTagName(String tagName) {
        Map<String, String> namedParameters = Collections.singletonMap("name", tagName);
        return this.namedParameterJdbcTemplate.query(SELECT_CERTIFICATE_BY_TAG_NAME_QUERY, namedParameters, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public List<GiftCertificate> getAllByQuery(Map<String, String> queryParam) {
        StringBuilder sql = new StringBuilder(SELECT_ALL_CERTIFICATES_QUERY);
        if (queryParam.containsKey("name")) {
            queryParam.put("partName","%"+queryParam.get("name")+"%");
            sql.append(" WHERE t.name = :name OR gc.name like :partName OR gc.description like :partName");
        }
        sql.append(" GROUP BY gc.id");
        if (queryParam.containsKey("sort")) {
            sql.append(" ORDER BY gc.").append(queryParam.get("sort"));
            if (queryParam.containsKey("order")) {
                sql.append(" ").append(queryParam.get("order"));
            }
        }
        return this.namedParameterJdbcTemplate.query(sql.toString(), queryParam, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }
}
