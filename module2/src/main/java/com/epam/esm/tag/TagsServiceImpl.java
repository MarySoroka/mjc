package com.epam.esm.tag;

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
    public void createTag(Tag tag) {
        Long tagId = tagsDao.save(tag);
        tag.setId(tagId);
    }

    @Override
    public void deleteTag(Long tagId) throws TagDeleteException {
        boolean isDeleted = tagsDao.delete(tagId);
        if (!isDeleted) {
            throw new TagDeleteException("Couldn't delete tag by id: " + tagId);
        }
    }


}
