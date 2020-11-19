package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderRepository;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
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
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

  @PersistenceContext
  private EntityManager entityManager;


  @Override
  public Optional<Order> getById(Long id) {
    return Optional.ofNullable(entityManager.find(Order.class, id));
  }

  @Override
  public List<Order> getAll( Integer limit, Integer offset) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Order> criteriaQuery = criteriaBuilder
        .createQuery(Order.class);
    Root<Order> from = criteriaQuery.from(Order.class);
    CriteriaQuery<Order> select = criteriaQuery.select(from);
    TypedQuery<Order> typedQuery = entityManager.createQuery(select);
    typedQuery.setFirstResult(offset);
    typedQuery.setMaxResults(limit);
    return typedQuery.getResultList();

  }

  @Override
  public void delete(Long id) throws RepositoryDeleteException {
    Order order = entityManager.find(Order.class, id);
    if (entityManager.contains(order)) {
      entityManager.remove(order);
    } else {
      throw new RepositoryDeleteException("Repository exception: couldn't delete tsg by id: " + id);
    }
  }

  @Override
  public void update(Order order) {
    entityManager.merge(order);
  }

  @Override
  public Order save(Order order) {
    entityManager.persist(order);
    return order;
  }

  @Override
  public Set<Order> getAllUserOrders(Long userId, Integer limit, Integer offset) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Order> criteriaQuery = criteriaBuilder
        .createQuery(Order.class);
    Root<Order> orderRoot = criteriaQuery.from(Order.class);
    Root<User> userRoot = criteriaQuery.from(User.class);

    CriteriaQuery<Order> select = criteriaQuery.select(orderRoot);
    select.where(
        criteriaBuilder.equal(userRoot.get("user_id"), userId)
    );
    TypedQuery<Order> typedQuery = entityManager.createQuery(select);
    typedQuery.setFirstResult(offset);
    typedQuery.setMaxResults(limit);
    return new HashSet<>(typedQuery.getResultList());
  }
}
