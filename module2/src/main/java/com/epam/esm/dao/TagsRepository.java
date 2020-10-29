package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * interface with CRD operations for Tag entity
 */
public interface TagsRepository extends CRUDDao<Tag, Long> {
    List<Tag> getTagsByCertificateId(Long certificateId);
    Optional<Tag> getTagByName(String tagName);

    boolean saveCertificateTag(Long tagId, Long certificateId);
}
