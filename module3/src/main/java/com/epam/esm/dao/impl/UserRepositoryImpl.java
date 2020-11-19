package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserRepository;
import com.epam.esm.entity.User;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<User> getById(Long id) {
    return Optional.ofNullable(entityManager.find(User.class, id));
  }

  @Override
  public List<User> getAll( Integer limit, Integer offset) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
    Root<User> from = criteriaQuery.from(User.class);
    CriteriaQuery<User> select = criteriaQuery.select(from);
    TypedQuery<User> typedQuery = entityManager.createQuery(select);
    typedQuery.setFirstResult(offset);
    typedQuery.setMaxResults(limit);
    return typedQuery.getResultList();
  }

  @Override
  public void delete(Long aLong) {
    throw new UnsupportedOperationException();

  }

  @Override
  public void update(User user) {
    throw new UnsupportedOperationException();

  }

  @Override
  public User save(User user) {
    throw new UnsupportedOperationException();
  }
}
