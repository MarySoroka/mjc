package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

public interface CRUDDao<ENTITY, KEY> {
    Optional<ENTITY> getById(KEY key);
    List<ENTITY> getAll();
    boolean delete(KEY key);
    boolean update(ENTITY entity);
    Long save(ENTITY entity);

}
