package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoSaveException;

import java.util.List;
import java.util.Optional;

/**
 * interface with CRD operations for Tag entity
 */
public interface TagsRepository {
    /**
     * delete entity by key parameter
     *
     * @param id tag id
     * @return if entity has been found and deleted - true, else false
     */
    boolean delete(Long id);

    /**
     * method save entity
     *
     * @param tag tag, that should be save
     * @return if entity has been saved return generated id
     * @throws DaoSaveException if generated id equals null
     */
    Long save(Tag tag) throws DaoSaveException;

    /**
     * @return all objects in specific db table
     */
    List<Tag> getAll();

    /**
     * method find entity by key parameter
     *
     * @param id tag id, which is using to find entity
     * @return entity or Optional.empty(), if entity is not found by this key
     */
    Optional<Tag> getById(Long id);
}
