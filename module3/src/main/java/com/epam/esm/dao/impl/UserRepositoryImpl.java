package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserRepository;
import com.epam.esm.entity.User;
import com.epam.esm.exception.RepositorySaveException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private static final String SELECT_ALL_USERS_QUERY = "SELECT  u.id, u.name,u.surname FROM gift_certificates.user u";
  private static final String SELECT_USER_BY_ID_QUERY = "SELECT  u.id, u.name,u.surname FROM gift_certificates.user  u WHERE u.id = :id";
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  public UserRepositoryImpl(
      NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public Optional<User> getById(Long id) {
    SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
    List<User> queryForObject = this.namedParameterJdbcTemplate
        .query(SELECT_USER_BY_ID_QUERY, namedParameters, new BeanPropertyRowMapper<>(User.class));
    if (queryForObject.size() != 1) {
      return Optional.empty();
    }
    return Optional.ofNullable(queryForObject.get(0));
  }

  @Override
  public List<User> getAll() {
    return namedParameterJdbcTemplate
        .query(SELECT_ALL_USERS_QUERY, new BeanPropertyRowMapper<>(User.class));
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
  public Long save(User user) throws RepositorySaveException {
    throw new UnsupportedOperationException();
  }
}
