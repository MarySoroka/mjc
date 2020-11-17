package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class TagRepositoryImpl implements TagRepository {

  private static final String INSERT_TAG_QUERY = "INSERT INTO gift_certificates.tag (`name`) values (:name)";
  private static final String INSERT_TAG_CERTIFICATE_QUERY = "INSERT INTO gift_certificates.certificate_tag (tag_id, certificate_id) values (:tagId, :certificateId)";
  private static final String DELETE_CERTIFICATE_TAG_QUERY = "DELETE FROM gift_certificates.certificate_tag WHERE tag_id = :tagId AND certificate_id = :certificateId";
  private static final String DELETE_TAG_QUERY = "DELETE FROM gift_certificates.tag WHERE id = :id";
  private static final String SELECT_ALL_TAGS_QUERY = "SELECT id, name FROM gift_certificates.tag ORDER BY id LIMIT :limit OFFSET :offset";
  private static final String SELECT_TAG_BY_ID_QUERY = "SELECT id, name FROM gift_certificates.tag WHERE id= :id";
  private static final String SELECT_TAG_BY_NAME_QUERY = "SELECT id, name FROM gift_certificates.tag WHERE `name`= :name";
  private static final String SELECT_TAG_BY_CERTIFICATE_ID_QUERY = "SELECT t.id, t.name FROM gift_certificates.tag t JOIN gift_certificates.certificate_tag ct on t.id = ct.tag_id JOIN gift_certificates.gift_certificate gc on gc.id = ct.certificate_id WHERE gc.id =:id";
  private static final String SELECT_THE_MOST_WIDELY_USED_TAG_QUERY =
      "SELECT t.id, t.name FROM gift_certificates.`order` o " +
          "JOIN gift_certificates.gift_certificate gc on o.order_certificate_id = gc.id " +
          "JOIN gift_certificates.certificate_tag ct on gc.id = ct.certificate_id " +
          "JOIN gift_certificates.tag t on t.id = ct.tag_id " +
          "WHERE o.cost = (SELECT max(cost) FROM gift_certificates.`order`) " +
          "GROUP BY gc.id " +
          "ORDER BY count(t.id) DESC";
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  public TagRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

  }


  @Override
  public Optional<Tag> getById(Long id) {
    return Optional.ofNullable(entityManager.find(Tag.class, id));

  }

  @Override
  public List<Tag> getAll(Map<String, Integer> pagination) {
    int limit = Integer.parseInt(String.valueOf(pagination.get("limit")));
    int offset = Integer.parseInt(String.valueOf(pagination.get("offset")));
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
    Root<Tag> from = criteriaQuery.from(Tag.class);
    CriteriaQuery<Tag> select = criteriaQuery.select(from);
    TypedQuery<Tag> typedQuery = entityManager.createQuery(select);
    typedQuery.setFirstResult(offset);
    typedQuery.setMaxResults(limit);
    return typedQuery.getResultList();
  }

  @Override
  public void delete(Long id) throws RepositoryDeleteException {
    Tag tag = entityManager.find(Tag.class, id);
    if (entityManager.contains(tag)) {
      entityManager.remove(tag);
    } else {
      throw new RepositoryDeleteException("Repository exception: couldn't delete tsg by id: " + id);
    }
  }

  @Override
  public void update(Tag tag) {
    throw new UnsupportedOperationException();
  }


  @Override
  public Long save(Tag tag) {
    entityManager.persist(tag);
    return tag.getId();

  }

  @Override
  public Set<Tag> getTagsByCertificateId(Long certificateId) {
    SqlParameterSource namedParameters = new MapSqlParameterSource("id", certificateId);
    return new HashSet<>(this.namedParameterJdbcTemplate
        .query(SELECT_TAG_BY_CERTIFICATE_ID_QUERY, namedParameters,
            new BeanPropertyRowMapper<>(Tag.class)));
  }

  @Override
  public Optional<Tag> getTagByName(String tagName) {
    return Optional.ofNullable(entityManager.find(Tag.class, tagName));

  }

  @Override
  public void saveCertificateTag(Long tagId, Long certificateId) throws RepositorySaveException {
    SqlParameterSource namedParameters = new MapSqlParameterSource("tagId", tagId)
        .addValue("certificateId", certificateId);
    int update = this.namedParameterJdbcTemplate
        .update(INSERT_TAG_CERTIFICATE_QUERY, namedParameters);
    if (update == 0) {
      throw new RepositorySaveException(
          "Repository exception: Couldn't save certificate tag. Tag id :" + tagId);
    }
  }

  @Override
  public void deleteCertificateTag(Long tagId, Long certificateId)
      throws RepositoryDeleteException {
    SqlParameterSource namedParameters = new MapSqlParameterSource("tagId", tagId)
        .addValue("certificateId", certificateId);
    if (namedParameterJdbcTemplate.update(DELETE_CERTIFICATE_TAG_QUERY, namedParameters) == 0) {
      throw new RepositoryDeleteException(
          "Repository exception: Couldn't delete certificate tag entity with id : " + tagId);
    }

  }

  @Override
  public Tag getTheMostWidelyUsedTag() {
    return namedParameterJdbcTemplate
        .query(SELECT_THE_MOST_WIDELY_USED_TAG_QUERY, new BeanPropertyRowMapper<>(Tag.class))
        .get(0);
  }

}
