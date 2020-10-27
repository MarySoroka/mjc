package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.exception.TagServiceException;

import java.util.List;
/**
 * interface that demonstrate business logic for tag domain
 */
public interface TagsService {

    /**
     * @return all objects in specific db table
     */
    List<Tag> getAllTags();

    /**
     * method find entity by key parameter
     *
     * @param id tag id, which is using to find entity
     * @return entity or Optional.empty(), if entity is not found by this key
     */
    Tag getTagById(Long id) throws TagNotFoundException;

    /**
     * method save entity
     *
     * @param tag tag, that should be save
     * @return if entity has been saved return generated id
     * @throws TagServiceException if generated id equals null
     */
    void createTag(Tag tag) throws TagServiceException;

    /**
     * delete entity by key parameter
     *
     * @param tagId tag id
     * @return if entity has been found and deleted - true, else false
     */
    void deleteTag(Long tagId) throws TagServiceException;


}
