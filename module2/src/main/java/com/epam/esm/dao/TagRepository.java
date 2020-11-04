package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import java.util.List;
import java.util.Optional;

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
  List<Tag> getTagsByCertificateId(Long certificateId);

  /**
   * find tag by name
   *
   * @param tagName tag name
   * @return Optional entity of tag, if tag is presents or Optional.empty()
   */
  Optional<Tag> getTagByName(String tagName);

  /**
   * method save tag for this certificate
   *
   * @param tagId         tag id
   * @param certificateId cartificate id
   * @throws RepositorySaveException if tag was not save
   */
  void saveCertificateTag(Long tagId, Long certificateId) throws RepositorySaveException;

  /**
   * delete certificate tag
   *
   * @param tagId         tag id
   * @param certificateId certificate id
   * @throws RepositoryDeleteException if tag was not deleted
   */
  void deleteCertificateTag(Long tagId, Long certificateId) throws RepositoryDeleteException;
}
