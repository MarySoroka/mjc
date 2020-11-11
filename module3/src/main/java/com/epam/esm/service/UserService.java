package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import java.util.List;

public interface UserService {
     List<User> getAllUsers();
     User getById(Long id) throws EntityNotFoundException;
}
