package com.epam.esm.service;


import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.epam.esm.dao.TagRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.exception.TagServiceException;
import com.epam.esm.service.impl.TagServiceImpl;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {


  @Mock
  private TagRepository tagRepository;

  @InjectMocks
  private TagServiceImpl tagsService;

  @Test
  void whenMockGetAllTagsThenReturnEmptyList() {
    HashMap<String, Long> pagination = new HashMap<>();
    when(tagRepository.getAll(pagination)).thenReturn(new LinkedList<>());
    List<Tag> tags = tagsService.getAllTags(pagination);
    assertEquals(0, tags.size());
  }

  @Test
  void whenMockGetTagByIdThenReturnTag() throws EntityNotFoundException {
    Tag expectedTag = new Tag(1L, "expectedTag");
    when(tagRepository.getById(anyLong())).thenReturn(of(expectedTag));

    Tag actualTag = tagsService.getTagById(1L);

    assertEquals(expectedTag, actualTag);
  }

  @Test
  void whenMockCreateTagThenReturnId()
      throws RepositorySaveException, TagServiceException, EntityNotFoundException {
    Tag expectedTag = new Tag(null, "name");
    when(tagRepository.save(expectedTag)).thenReturn(1L);
    when(tagRepository.getById(1L)).thenReturn(of(expectedTag));

    Tag actualTag = tagsService.createTag(expectedTag);

    expectedTag.setId(1L);

    assertEquals(expectedTag, actualTag);
  }

  @Test
  void whenMockDeleteTagThenDoesNotThrowException() throws RepositoryDeleteException {
    doNothing().when(tagRepository).delete(anyLong());

    assertDoesNotThrow(() -> tagsService.deleteTag(1L));
  }
}