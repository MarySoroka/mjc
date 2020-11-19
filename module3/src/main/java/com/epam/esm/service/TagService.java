package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.dto.TagDTO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * interface that demonstrate business logic for tag domain
 */
public interface TagService {

  /**
   * @return all objects in specific db table
   */


  List<Tag> getAllTags(Integer limit, Integer offset);

  /**
   * method find entity by key parameter
   *
   * @param id tag id, which is using to find entity
   * @return entity or Optional.empty(), if entity is not found by this key
   */
  Tag getTagById(Long id) throws EntityNotFoundException;

  /**
   * method save entity
   *
   * @param tag tag, that should be save
   * @return if entity has been saved return generated id
   */
  Tag createTag(TagDTO tag) throws RepositorySaveException, EntityNotFoundException;

  /**
   * delete entity by key parameter
   *
   * @param tagId tag id
   * @return if entity has been found and deleted - true, else false
   */
  void deleteTag(Long tagId) throws RepositoryDeleteException;

  /**
   * method find certificate tags
   *
   * @param certificateId certificate id
   * @return certificate tags
   */
  Set<Tag> getTagsByCertificateId(Long certificateId, Integer limit, Integer offset);

  /**
   * method finds tag by tag
   *
   * @param tagName tag name
   * @return Optional entity of tag if tag is presents or Optional.empty() if tag is absent
   */
  Optional<Tag> getTagByName(String tagName);


  Tag getTheMostWidelyUsedTag();
}
