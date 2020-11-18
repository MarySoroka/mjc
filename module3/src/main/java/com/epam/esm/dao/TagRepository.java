package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * interface with CRD operations for Tag entity
 */
public interface TagRepository extends CRUDDao<Tag, Long> {

  /**
   * method find tags by certificate id
   *
   * @param certificateId certificate id
   * @return tags, that are in certificate with this id
   */
  Set<Tag> getTagsByCertificateId(Long certificateId, Map<String, Integer> pagination);

  /**
   * find tag by name
   *
   * @param tagName tag name
   * @return Optional entity of tag, if tag is presents or Optional.empty()
   */
  Optional<Tag> getTagByName(String tagName);



  Tag getTheMostWidelyUsedTag();

}
