package com.epam.esm.dao;

import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;

import com.epam.esm.exception.RepositoryUpdateException;
import java.util.List;
import java.util.Optional;

/**
 * interface for CRUD operations in repositories
 *
 * @param <E> entity
 * @param <K> key for entity
 */
public interface CRUDDao<E, K> {
    /**
     * method find entity by key parameter
     *
     * @param k key, which is using to find entity
     * @return entity or Optional.empty(), if entity is not found by this key
     */
    Optional<E> getById(K k);

    /**
     * @return all objects in specific db table
     */
    List<E> getAll();

    /**
     * delete entity by key parameter
     *
     * @param k key
     */
    void delete(K k) throws RepositoryDeleteException;

    /**
     * method update entity using new values in e
     *
     * @param e new values for entity
     */
    void update(E e) throws RepositoryUpdateException;

    /**
     * method save entity
     *
     * @param e entity, that should be save
     * @return if entity has been saved return generated id
     * @throws RepositorySaveException if generated id equals null
     */
    K save(E e) throws RepositorySaveException;

}
