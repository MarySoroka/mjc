package com.epam.esm.service.impl;

import com.epam.esm.dao.UserRepository;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.UserService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> getAllUsers(Map<String,Integer> pagination) {
    return userRepository.getAll(pagination);
  }

  @Override
  public User getById(Long id) throws EntityNotFoundException {
    Optional<User> user = userRepository.getById(id);
    if (user.isPresent()){
      return user.get();
    }
     throw new EntityNotFoundException("Service exception : Couldn't get user by id: " + id);
  }
}
