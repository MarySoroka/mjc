package com.epam.esm.tag;

import java.util.List;
import java.util.Optional;

public interface TagsDao {
    boolean delete(Long id);
    Long save(Tag tag) throws TagSaveException;
    List<Tag> getAll();
    Optional<Tag> getById(Long id);
}
