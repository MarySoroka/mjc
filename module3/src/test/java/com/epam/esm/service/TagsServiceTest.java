package com.epam.esm.service;


import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.esm.dao.TagsRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.exception.TagServiceException;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagsServiceTest {


  @Mock
  private TagsRepository tagsRepository;

  @InjectMocks
  private TagsServiceImpl tagsService;

  @Test
  void whenMockGetAllTagsThenReturnEmptyList() {
    when(tagsRepository.getAll()).thenReturn(new LinkedList<>());
    List<Tag> tags = tagsService.getAllTags();
    assertEquals(0, tags.size());
  }

  @Test
  void whenMockGetTagByIdThenReturnTag() throws TagNotFoundException {
    when(tagsRepository.getById(anyLong())).thenReturn(of(new Tag()));
    Tag tagById = tagsService.getTagById(1L);
    assertNotNull(tagById);
  }

  @Test
  void whenMockCreateTagThenReturnId() throws RepositorySaveException, TagServiceException {
    Tag tag = new Tag(1L, "name");
    when(tagsRepository.save(tag)).thenReturn(1L);
    tagsService.createTag(tag);
    assertEquals(1L, tag.getId());
  }

  @Test
  void whenMockDeleteTagThenReturnTrue() throws TagServiceException, RepositoryDeleteException {
    doNothing().when(tagsRepository).delete(anyLong());
    tagsService.deleteTag(1L);
    verify(tagsRepository).delete(anyLong());
  }
}