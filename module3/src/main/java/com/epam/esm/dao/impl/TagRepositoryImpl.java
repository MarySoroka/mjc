package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RepositoryDeleteException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.springframework.stereotype.Repository;

@Repository
public class TagRepositoryImpl implements TagRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Tag getTheMostWidelyUsedTag() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
    Root<Tag> tagRoot = query.from(Tag.class);
    query.select(tagRoot);
    Subquery<Long> subQuery = query.subquery(Long.class);
    Root<Order> orderRoot = subQuery.from(Order.class);
    Root<GiftCertificate> giftCertificateRoot = subQuery.from(GiftCertificate.class);
    Join<GiftCertificate, Tag> giftCertificateJoin = giftCertificateRoot.join("tags");
    Join<GiftCertificate, Order> orderJoin = giftCertificateJoin.join("orders");

    subQuery.select(criteriaBuilder.max(orderJoin.get("cost")));
    query.where(
        criteriaBuilder.equal(orderRoot.get("cost"), subQuery)
    );
    return entityManager.createQuery(query).getSingleResult();
  }

  @Override
  public Optional<Tag> getById(Long id) {
    return Optional.ofNullable(entityManager.find(Tag.class, id));

  }

  @Override
  public List<Tag> getAll(Integer limit, Integer offset) {
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
  public Tag save(Tag tag) {
    entityManager.persist(tag);
    return tag;

  }

  @Override
  public Set<Tag> getTagsByCertificateId(Long certificateId, Integer limit, Integer offset) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
    Root<Tag> tagRoot = criteriaQuery.from(Tag.class);
    Root<GiftCertificate> giftCertificateRoot = criteriaQuery.from(GiftCertificate.class);

    CriteriaQuery<Tag> select = criteriaQuery.select(tagRoot);
    select.where(
        criteriaBuilder.equal(giftCertificateRoot.get("certificate_id"), certificateId)
    );
    TypedQuery<Tag> typedQuery = entityManager.createQuery(select);
    typedQuery.setFirstResult(offset);
    typedQuery.setMaxResults(limit);
    return new HashSet<>(typedQuery.getResultList());
  }

  @Override
  public Optional<Tag> getTagByName(String tagName) {
    return Optional.ofNullable(entityManager.find(Tag.class, tagName));

  }


}
