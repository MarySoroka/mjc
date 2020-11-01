package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import java.util.List;
import java.util.Optional;

/**
 * interface with CRD operations for Tag entity
 */
public interface TagsRepository extends CRUDDao<Tag, Long> {
    List<Tag> getTagsByCertificateId(Long certificateId);
    Optional<Tag> getTagByName(String tagName);
    Long saveCertificateTag(Long tagId, Long certificateId) throws RepositorySaveException;
    void deleteCertificateTag(Long tagId, Long certificateId) throws RepositoryDeleteException;
}
