package com.epam.esm.service;


import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.epam.esm.dao.TagRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.dto.TagDTO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.service.impl.TagServiceImpl;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

  Tag tag = new Tag(1L, "name");
  @Mock
  private TagRepository tagRepository;

  @InjectMocks
  private TagServiceImpl tagsService;

  @Test
  void whenMockGetAllTagsThenReturnEmptyList() {
    when(tagRepository.getAll(anyInt(),anyInt())).thenReturn(new LinkedList<>());
    List<Tag> tags = tagsService.getAllTags(10,0);
    assertEquals(0, tags.size());
  }

  @Test
  void whenMockGetTagsByCertificateIdThenReturnEmptyList() {
    when(tagRepository.getTagsByCertificateId(anyLong(),anyInt(),anyInt())).thenReturn(new HashSet<>());
    Set<Tag> tags = tagsService.getTagsByCertificateId(1L,10,0);
    assertEquals(0, tags.size());
  }

  @Test
  void whenMockExceptionGetTagByIdThenThrowsException() {
    when(tagRepository.getById(anyLong())).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> tagsService.getTagById(1L));
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
      throws RepositorySaveException {
    Tag expectedTag = new Tag(null, "name");
    when(tagRepository.save(expectedTag)).thenReturn(tag);

    Tag actualTag = tagsService.createTag(new TagDTO(expectedTag));

    expectedTag.setId(1L);

    assertEquals(expectedTag, actualTag);
  }

  @Test
  void whenMockDeleteTagThenDoesNotThrowException() throws RepositoryDeleteException {
    doNothing().when(tagRepository).delete(anyLong());

    assertDoesNotThrow(() -> tagsService.deleteTag(1L));
  }



  @Test
  void whenMockGetTagByNameThenReturnTag() {
    when(tagRepository.getTagByName(anyString())).thenReturn(ofNullable(tag));
    Optional<Tag> tagByName = tagsService.getTagByName("name");
    assertTrue(tagByName.isPresent());
  }

  @Test
  void whenMockGetTagByNameThenReturnOptionalEmpty() {
    when(tagRepository.getTagByName(anyString())).thenReturn(Optional.empty());
    Optional<Tag> tagByName = tagsService.getTagByName("name");
    assertFalse(tagByName.isPresent());
  }



  @Test
  void whenMockGetTheMostWidelyUsedTagThenReturnTag() {
    when(tagRepository.getTheMostWidelyUsedTag()).thenReturn(tag);
    assertEquals(tag, tagsService.getTheMostWidelyUsedTag());
  }
}