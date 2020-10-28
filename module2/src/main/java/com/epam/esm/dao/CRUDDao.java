package com.epam.esm.dao;

import com.epam.esm.exception.DaoSaveException;

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
     * @return if entity has been found and deleted - true, else false
     */
    boolean delete(K k);

    /**
     * method update entity using new values in e
     *
     * @param e new values for entity
     * @return if entity has been found and updated - true, else false
     */
    boolean update(E e);

    /**
     * method save entity
     *
     * @param e entity, that should be save
     * @return if entity has been saved return generated id
     * @throws DaoSaveException if generated id equals null
     */
    K save(E e) throws DaoSaveException;

}
