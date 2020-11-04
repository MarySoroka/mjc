package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.exception.RepositoryUpdateException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

  private static final String INSERT_CERTIFICATES_QUERY = "INSERT INTO gift_certificates.gift_certificate (name,description, price, create_date, last_update_date, duration ) values (:name,:description,:price,:createDate,:lastUpdateDate,:duration)";
  private static final String UPDATE_CERTIFICATES_QUERY = "UPDATE gift_certificates.gift_certificate set name=:name ,description=:description, price=:price, create_date=:createDate, last_update_date=:lastUpdateDate, duration=:duration where id = :id";

  private static final String DELETE_CERTIFICATES_QUERY = "DELETE FROM gift_certificates.gift_certificate WHERE id = :id";
  private static final String SELECT_ALL_CERTIFICATES_QUERY = "SELECT gc.id,gc.name,gc.description, gc.price, gc.create_date, gc.last_update_date, gc.duration FROM gift_certificates.gift_certificate gc JOIN gift_certificates.certificate_tag ct on gc.id = ct.certificate_id JOIN gift_certificates.tag t on t.id = ct.tag_id";
  private static final String SELECT_CERTIFICATE_BY_ID_QUERY = "SELECT gc.id,gc.name,gc.description, gc.price, gc.create_date, gc.last_update_date, gc.duration FROM gift_certificates.gift_certificate gc  WHERE gc.id = :id";
  private static final String SELECT_CERTIFICATE_BY_TAG_NAME_QUERY = "SELECT gc.id,gc.name,gc.description, gc.price, gc.create_date, gc.last_update_date, gc.duration FROM gift_certificates.gift_certificate gc JOIN gift_certificates.certificate_tag ct on gc.id = ct.certificate_id JOIN gift_certificates.tag t on t.id = ct.tag_id WHERE t.name= :name";

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  public GiftCertificateRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }


  @Override
  public Optional<GiftCertificate> getById(Long id) {
    SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
    List<GiftCertificate> queryForObject = this.namedParameterJdbcTemplate
        .query(SELECT_CERTIFICATE_BY_ID_QUERY, namedParameters,
            new BeanPropertyRowMapper<>(GiftCertificate.class));
    if (queryForObject.size() != 1) {
      return Optional.empty();
    }
    return Optional.ofNullable(queryForObject.get(0));
  }

  @Override
  public List<GiftCertificate> getAll() {
    return namedParameterJdbcTemplate
        .query(SELECT_ALL_CERTIFICATES_QUERY, new BeanPropertyRowMapper<>(GiftCertificate.class));
  }

  @Override
  public void delete(Long id) throws RepositoryDeleteException {
    SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
    if (namedParameterJdbcTemplate.update(DELETE_CERTIFICATES_QUERY, namedParameters) != 0) {
      throw new RepositoryDeleteException(
          "Repository exception: Couldn't delete gift certificate entity with id : " + id);
    }
  }


  @Override
  public Long save(GiftCertificate giftCertificate) throws RepositorySaveException {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(giftCertificate);
    namedParameterJdbcTemplate
        .update(INSERT_CERTIFICATES_QUERY, namedParameters, keyHolder, new String[]{"id"});
    Number key = keyHolder.getKey();
    if (key == null) {
      throw new RepositorySaveException("Repository exception: Couldn't save gift certificate");
    }
    return key.longValue();

  }

  @Override
  public void update(GiftCertificate giftCertificate) throws RepositoryUpdateException {
    SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(giftCertificate);
    int isUpdate = namedParameterJdbcTemplate.update(UPDATE_CERTIFICATES_QUERY, namedParameters);
    if (isUpdate == 0) {
      throw new RepositoryUpdateException(
          "Repository exception: Couldn't update gift certificate with id : " + giftCertificate
              .getId());
    }
  }

  @Override
  public List<GiftCertificate> getGiftCertificatesByTagName(String tagName) {
    SqlParameterSource namedParameters = new MapSqlParameterSource("name", tagName);
    return this.namedParameterJdbcTemplate
        .query(SELECT_CERTIFICATE_BY_TAG_NAME_QUERY, namedParameters,
            new BeanPropertyRowMapper<>(GiftCertificate.class));
  }

  @Override
  public List<GiftCertificate> getAllByQuery(Map<String, String> queryParam) {
    StringBuilder sql = new StringBuilder(SELECT_ALL_CERTIFICATES_QUERY);
    if (queryParam.containsKey("name")) {
      queryParam.put("partName", "%" + queryParam.get("name") + "%");
      sql.append(
          " WHERE t.name = :name OR gc.name like :partName OR gc.description like :partName");
    }
    sql.append(" GROUP BY gc.id");
    if (queryParam.containsKey("sort")) {
      sql.append(" ORDER BY gc.").append(queryParam.get("sort"));
      if (queryParam.containsKey("order")) {
        sql.append(" ").append(queryParam.get("order"));
      }
    }
    return this.namedParameterJdbcTemplate
        .query(sql.toString(), queryParam, new BeanPropertyRowMapper<>(GiftCertificate.class));
  }
}
