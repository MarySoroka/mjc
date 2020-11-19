package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import java.util.List;

public interface UserService {

  List<User> getAllUsers(Integer limit, Integer offset);

  User getById(Long id) throws EntityNotFoundException;
}
