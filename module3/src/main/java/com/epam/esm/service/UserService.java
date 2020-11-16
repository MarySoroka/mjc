package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import java.util.List;
import java.util.Map;

public interface UserService {
     List<User> getAllUsers( Map<String,Integer> pagination);
     User getById(Long id) throws EntityNotFoundException;
}
