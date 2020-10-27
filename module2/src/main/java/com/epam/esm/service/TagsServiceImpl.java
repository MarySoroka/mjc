package com.epam.esm.service;

import com.epam.esm.dao.TagsDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.exception.TagSaveException;
import com.epam.esm.exception.TagServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagsServiceImpl implements TagsService {

    private final TagsDao tagsDao;

    @Autowired
    public TagsServiceImpl(TagsDao tagsDao) {
        this.tagsDao = tagsDao;
    }


    @Override
    public List<Tag> getAllTags() {
        return tagsDao.getAll();
    }

    @Override
    public Tag getTagById(Long id) throws TagNotFoundException {
        Optional<Tag> tagsDaoById = tagsDao.getById(id);
        if (tagsDaoById.isPresent()) {
            return tagsDaoById.get();
        } else {
            throw new TagNotFoundException("Couldn't find tag by id: " + id);
        }
    }

    @Override
    public void createTag(Tag tag) throws TagServiceException {
        try {
            Long tagId = tagsDao.save(tag);
            tag.setId(tagId);
        } catch (TagSaveException e) {
            throw new TagServiceException("Couldn't create tag: ", e);
        }
    }

    @Override
    public void deleteTag(Long tagId) throws TagServiceException {
        boolean isDeleted = tagsDao.delete(tagId);
        if (!isDeleted) {
            throw new TagServiceException("Couldn't delete tag by id: " + tagId);
        }
    }


}
