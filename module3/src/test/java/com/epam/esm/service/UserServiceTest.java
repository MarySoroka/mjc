package com.epam.esm.service;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.epam.esm.dao.UserRepository;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.impl.UserServiceImpl;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  private static final Map<String, Integer> pagination = new HashMap<>(2);
  User user = new User(1L, "name", "surname");
  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserServiceImpl userService;

  @BeforeAll
  public static void setup() {
    pagination.put("limit", 10);
    pagination.put("offset", 0);
  }

  @Test
  void whenMockGetAllUsersThenReturnOneUser() {
    when(userRepository.getAll(eq(pagination))).thenReturn(Collections.singletonList(user));
    List<User> allUsers = userService.getAllUsers(pagination);
    assertEquals(1L, allUsers.size());
  }

  @Test
  void whenMockGetByIdUserThenReturnUser() throws EntityNotFoundException {
    when(userRepository.getById(anyLong())).thenReturn(ofNullable(user));
    assertEquals(user, userService.getById(1L));
  }

  @Test
  void whenMockGetByIdUserThenThrowsException() {
    when(userRepository.getById(anyLong())).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> userService.getById(1L));
  }
}