package com.epam.esm.service.impl;

import com.epam.esm.dao.TagRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.service.TagService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;

  @Autowired
  public TagServiceImpl(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }


  @Override
  public List<Tag> getAllTags(Map<String,Integer> pagination) {
    return tagRepository.getAll(pagination);
  }


  @Override
  public Tag getTagById(Long id) throws EntityNotFoundException {
    Optional<Tag> tagsDaoById = tagRepository.getById(id);
    if (tagsDaoById.isPresent()) {
      return tagsDaoById.get();
    } else {
      throw new EntityNotFoundException("Service exception : Couldn't get tag by id: " + id);
    }
  }

  @Override
  @Transactional
  public Tag createTag(Tag tag)
      throws RepositorySaveException, EntityNotFoundException {
    Long tagId = tagRepository.save(tag);
    return getTagById(tagId);

  }

  @Override
  @Transactional
  public void deleteTag(Long tagId) throws RepositoryDeleteException {
    tagRepository.delete(tagId);
  }

  @Override
  public Set<Tag> getTagsByCertificateId(Long certificateId) {
    return tagRepository.getTagsByCertificateId(certificateId);
  }

  @Override
  public Optional<Tag> getTagByName(String tagName) {
    return tagRepository.getTagByName(tagName);
  }

  @Override
  public void deleteTagForCertificate(Long tagId, Long certificateId)
      throws RepositoryDeleteException {
    tagRepository.deleteCertificateTag(tagId, certificateId);
  }

  @Override
  @Transactional
  public void saveCertificateTag(Tag tag, Long certificateId)
      throws RepositorySaveException, EntityNotFoundException {
    Optional<Tag> tagByName = getTagByName(tag.getName());
    if (!tagByName.isPresent()) {
      createTag(tag);
      tagRepository.saveCertificateTag(tag.getId(), certificateId);
    } else {
      tagRepository.saveCertificateTag(tagByName.get().getId(), certificateId);
    }
  }

  @Override
  public Tag getTheMostWidelyUsedTag() {
    return tagRepository.getTheMostWidelyUsedTag();
  }


}
