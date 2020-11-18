package com.epam.esm.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.esm.dao.impl.UserRepositoryImpl;
import com.epam.esm.entity.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

class UserRepositoryTest {

 private static UserRepository userRepository;
  private final User user = new User(1L, "Mary", "D");

  @BeforeAll
  public static void setup() {
    DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
        .generateUniqueName(true)
        .addScript("classpath:database.sql")
        .addScript("classpath:test-data.sql")
        .build();
    NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    userRepository = new UserRepositoryImpl(jdbcTemplate);

  }

  @Test
  void getAllUsersThenReturnTwoUsers() {
    List<User> userRepositoryAll = userRepository.getAll(10,0);
    assertEquals(2L, userRepositoryAll.size());
  }

  @Test
  void getExistingUserByIdThenReturnCorrectUser() {
    Optional<User> user = userRepository.getById(1L);
    assertTrue(user.isPresent());
    User userExpected = user.get();
    assertEquals(1L, userExpected.getId());
  }

  @Test
  void getNotExistingUserByIdThenReturnOptionalEmpty() {
    Optional<User> user = userRepository.getById(100L);
    assertFalse(user.isPresent());
  }

  @Test
  void whenUpdateUserThenReturnException() {
    assertThrows(UnsupportedOperationException.class, () ->
        userRepository.update(user)
    );
  }

  @Test
  void whenSaveUserThenReturnException() {
    assertThrows(UnsupportedOperationException.class, () ->
        userRepository.save(user)
    );
  }

  @Test
  void whenDeleteUserThenReturnException() {
    assertThrows(UnsupportedOperationException.class, () ->
        userRepository.delete(1L)
    );
  }

}