package com.epam.esm.tag;

import java.util.List;

public interface TagsService {
    List<Tag> getAllTags();
    Tag getTagById(Long id) throws TagNotFoundException;
    void createTag(Tag tag) throws TagServiceException;
    void deleteTag(Long tagId) throws  TagServiceException;


}
