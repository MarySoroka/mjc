package com.epam.esm.service.impl;

import com.epam.esm.dao.TagRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.dto.TagDTO;
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
  public List<Tag> getAllTags(Map<String, Integer> pagination) {
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
  public Tag createTag(TagDTO tag)
      throws RepositorySaveException, EntityNotFoundException {
    return tagRepository.save(new Tag(tag));

  }

  @Override
  @Transactional
  public void deleteTag(Long tagId) throws RepositoryDeleteException {
    tagRepository.delete(tagId);
  }

  @Override
  public Set<Tag> getTagsByCertificateId(Long certificateId,Map<String, Integer> pagination) {
    return tagRepository.getTagsByCertificateId(certificateId, pagination);
  }

  @Override
  public Optional<Tag> getTagByName(String tagName) {
    return tagRepository.getTagByName(tagName);
  }



  @Override
  public Tag getTheMostWidelyUsedTag() {
    return tagRepository.getTheMostWidelyUsedTag();
  }


}
