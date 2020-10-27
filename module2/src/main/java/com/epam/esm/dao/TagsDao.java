package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagSaveException;

import java.util.List;
import java.util.Optional;

public interface TagsDao {
    boolean delete(Long id);
    Long save(Tag tag) throws TagSaveException;
    List<Tag> getAll();
    Optional<Tag> getById(Long id);
}
