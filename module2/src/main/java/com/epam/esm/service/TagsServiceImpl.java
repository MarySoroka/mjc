package com.epam.esm.service;

import com.epam.esm.dao.TagsRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.exception.TagServiceException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
      throw new TagNotFoundException("Service exception : Couldn't get tag by id: " + id);
    }
  }

  @Override
  @Transactional
  public Long createTag(Tag tag) throws TagServiceException {
    try {
      Long tagId = tagsRepository.save(tag);
      tag.setId(tagId);
      return tagId;
    } catch (RepositorySaveException e) {
      throw new TagServiceException("Service exception : Couldn't save tag ", e);
    }
  }

  @Override
  @Transactional
  public void deleteTag(Long tagId) throws TagServiceException {
    try {
      tagsRepository.delete(tagId);
    } catch (RepositoryDeleteException e) {
      throw new TagServiceException("Service exception : Couldn't delete tag ");
    }

  }

  @Override
  public List<Tag> getTagsByCertificateId(Long certificateId) {
    return tagsRepository.getTagsByCertificateId(certificateId);
  }

  @Override
  public Optional<Tag> getTagByName(String tagName) {
    return tagsRepository.getTagByName(tagName);
  }

  @Override
  public void deleteTagForCertificate(Long tagId, Long certificateId) throws TagServiceException {
    try {
      tagsRepository.deleteCertificateTag(tagId, certificateId);
    } catch (RepositoryDeleteException e) {
      throw new TagServiceException("Service exception : Couldn't delete certificate tag");
    }
  }

  @Override
  @Transactional
  public Long saveCertificateTag(Tag tag, Long certificateId) throws TagServiceException {
    try {
      Optional<Tag> tagByName = getTagByName(tag.getName());
      if (!tagByName.isPresent()) {
        createTag(tag);
        return tagsRepository.saveCertificateTag(tag.getId(), certificateId);
      }
      return tagsRepository.saveCertificateTag(tagByName.get().getId(), certificateId);
    } catch (RepositorySaveException e) {
      throw new TagServiceException(
          "Service exception : Couldn't save certificate tag. Certificate id " + certificateId);
    }
  }


}
