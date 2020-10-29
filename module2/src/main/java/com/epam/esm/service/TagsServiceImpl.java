package com.epam.esm.service;

import com.epam.esm.dao.TagsRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoSaveException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.exception.TagServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagsServiceImpl implements TagsService {

    private final TagsRepository tagsRepository;

    @Autowired
    public TagsServiceImpl(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }


    @Override
    public List<Tag> getAllTags() {
        return tagsRepository.getAll();
    }

    @Override
    public Tag getTagById(Long id) throws TagNotFoundException {
        Optional<Tag> tagsDaoById = tagsRepository.getById(id);
        if (tagsDaoById.isPresent()) {
            return tagsDaoById.get();
        } else {
            throw new TagNotFoundException("Couldn't find tag by id: " + id);
        }
    }

    @Override
    @Transactional
    public void createTag(Tag tag) throws TagServiceException {
        try {
            Long tagId = tagsRepository.save(tag);
            tag.setId(tagId);
        } catch (DaoSaveException e) {
            throw new TagServiceException("Couldn't create tag: ", e);
        }
    }

    @Override
    @Transactional
    public void deleteTag(Long tagId) throws TagServiceException {
        boolean isDeleted = tagsRepository.delete(tagId);
        if (!isDeleted) {
            throw new TagServiceException("Couldn't delete tag by id: " + tagId);
        }
    }

    @Override
    public List<Tag> getTagsByCertificateId(Long certificateId) {
        return tagsRepository.getTagsByCertificateId(certificateId);
    }


}
