package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.exception.TagServiceException;

import java.util.List;

public interface TagsService {
    List<Tag> getAllTags();
    Tag getTagById(Long id) throws TagNotFoundException;
    void createTag(Tag tag) throws TagServiceException;
    void deleteTag(Long tagId) throws  TagServiceException;


}
