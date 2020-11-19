package com.epam.esm.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.esm.entity.User;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class UserRepositoryTest {

  private final User user = new User(null, "Mary", "D");
  @Autowired
  private UserRepository userRepository;
  private Long userId;
  @Autowired
  private EntityManager entityManager;


  @Test
  @Transactional
  void getAllUsersThenReturnOneUser() {
    entityManager.persist(user);
    userId = user.getId();
    List<User> userRepositoryAll = userRepository.getAll(10, 0);
    assertEquals(1L, userRepositoryAll.size());
  }

  @Test
  @Transactional
  void getExistingUserByIdThenReturnCorrectUser() {
    entityManager.persist(user);
    userId = user.getId();
    Optional<User> user = userRepository.getById(userId);
    assertTrue(user.isPresent());
    User userExpected = user.get();
    assertEquals(2L, userExpected.getId());
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