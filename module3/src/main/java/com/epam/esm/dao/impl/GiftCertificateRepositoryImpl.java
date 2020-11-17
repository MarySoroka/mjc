package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.dao.RepositoryUtils;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateFields;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RepositoryDeleteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

  private static final String SELECT_ALL_CERTIFICATES_QUERY = "SELECT gc.certificate_id,gc.name,gc.description, gc.price, gc.create_date, gc.last_update_date, gc.duration FROM gift_certificates.gift_certificate gc JOIN gift_certificates.certificate_tag ct on gc.certificate_id = ct.certificate_id JOIN gift_certificates.tag t on t.tag_id = ct.tag_id ";

  @PersistenceContext
  private EntityManager entityManager;


  @Override
  public Optional<GiftCertificate> getById(Long id) {
    return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
  }

  @Override
  public List<GiftCertificate> getAll(Map<String, Integer> pagination) {
    int limit = Integer.parseInt(String.valueOf(pagination.get("limit")));
    int offset = Integer.parseInt(String.valueOf(pagination.get("offset")));
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder
        .createQuery(GiftCertificate.class);
    Root<GiftCertificate> from = criteriaQuery.from(GiftCertificate.class);
    CriteriaQuery<GiftCertificate> select = criteriaQuery.select(from);
    TypedQuery<GiftCertificate> typedQuery = entityManager.createQuery(select);
    typedQuery.setFirstResult(offset);
    typedQuery.setMaxResults(limit);
    return typedQuery.getResultList();
  }

  @Override
  public void delete(Long id) throws RepositoryDeleteException {
    GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
    if (entityManager.contains(giftCertificate)) {
      entityManager.remove(giftCertificate);
    } else {
      throw new RepositoryDeleteException("Repository exception: couldn't delete tsg by id: " + id);
    }
  }


  @Override
  public GiftCertificate save(GiftCertificate giftCertificate) {
    entityManager.persist(giftCertificate);
    return giftCertificate;

  }

  @Override
  public void update(GiftCertificate giftCertificate) {
    entityManager.merge(giftCertificate);
  }

  @Override
  public List<GiftCertificate> getGiftCertificatesByTagName(String tagName,
      Map<String, Integer> pagination) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
    Root<Tag> from = query.from(Tag.class);
    Join<Tag, GiftCertificate> certificateJoin = from.join("tags");
    query.select(certificateJoin);
    query.where(
        criteriaBuilder.equal(
            from.get("name"), tagName)
    );
    return entityManager.createQuery(query).getResultList();
  }

  @Override
  public List<GiftCertificate> getAllByQuery(Map<String, String> queryParam) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
    Root<GiftCertificate> giftCertificateRoot = query.from(GiftCertificate.class);
    query.select(giftCertificateRoot);
    Map<String, String> resultFilterMap = RepositoryUtils.getFilterStringByParams(queryParam);
    query.groupBy(giftCertificateRoot.get("id"));
    resultFilterMap.forEach(
        (key, value) -> query.having(
            criteriaBuilder.and(
                criteriaBuilder.equal(
                    giftCertificateRoot.get(key), value
                )
            )
        )
    );
    if (queryParam.containsKey("sort") && GiftCertificateFields.of(queryParam.get("sort"))
        .isPresent()) {
      if (queryParam.get("order").equals("asc")) {
        query.orderBy(criteriaBuilder.asc(giftCertificateRoot.get(queryParam.get("sort"))));
      } else {
        query.orderBy(criteriaBuilder.desc(giftCertificateRoot.get(queryParam.get("sort"))));
      }

    }
    int limit = Integer.parseInt(String.valueOf(queryParam.get("limit")));
    int offset = Integer.parseInt(String.valueOf(queryParam.get("offset")));
    TypedQuery<GiftCertificate> typedQuery = entityManager.createQuery(query);
    typedQuery.setFirstResult(offset);
    typedQuery.setMaxResults(limit);
    return typedQuery.getResultList();
  }
}
