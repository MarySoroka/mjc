package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.dao.RepositoryUtils;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateFields;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RepositoryDeleteException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

  @PersistenceContext
  private EntityManager entityManager;


  @Override
  public Optional<GiftCertificate> getById(Long id) {
    return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
  }

  @Override
  public List<GiftCertificate> getAll(Integer limit, Integer offset) {
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
  @Transactional
  public void delete(Long id) throws RepositoryDeleteException {
    GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
    if (entityManager.contains(giftCertificate)) {
      entityManager.remove(giftCertificate);
    } else {
      throw new RepositoryDeleteException("Repository exception: couldn't delete tsg by id: " + id);
    }
  }


  @Override
  @Transactional
  public GiftCertificate save(GiftCertificate giftCertificate) {
    entityManager.persist(giftCertificate);
    return giftCertificate;

  }

  @Override
  @Transactional
  public void update(GiftCertificate giftCertificate) {
    entityManager.merge(giftCertificate);
  }

  @Override
  public List<GiftCertificate> getGiftCertificatesByTagName(String tagName, Integer limit, Integer offset) {
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
                criteriaBuilder.like(
                    giftCertificateRoot.get(key), "%" + value + "%"
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
