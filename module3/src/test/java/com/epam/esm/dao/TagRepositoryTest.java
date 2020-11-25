package com.epam.esm.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class TagRepositoryTest {

  private final Tag tag = new Tag(null, "name");
  @Autowired
  protected EntityManager entityManager;
  @Autowired
  private TagRepository tagRepository;

  @Test
  void whenGetByIdExistingTagFromDatabaseThenReturnCorrectTag() throws RepositorySaveException {
    tagRepository.save(tag);
    Optional<Tag> tagsRepositoryById = tagRepository.getById(1L);
    assertTrue(tagsRepositoryById.isPresent());
    Tag tag = tagsRepositoryById.get();
    assertEquals(this.tag, tag);
  }

  @Test
  void whenGetByIdNotExistingTagFromDatabaseThenReturnOptionalEmpty() {
    Optional<Tag> tagsRepositoryById = tagRepository.getById(10L);
    assertFalse(tagsRepositoryById.isPresent());
  }

  @Test
  void whenGetAllFromDatabaseThenReturnCorrectTagsCount() {
    List<Tag> tags = tagRepository.getAll(10, 0);
    assertEquals(1, tags.size());
  }

  @Test
  void whenDeleteExistingTagThenReturnTrue() throws RepositoryDeleteException {
    tagRepository.delete(1L);
    assertFalse(tagRepository.getById(1L).isPresent());
  }

  @Test
  void whenDeleteNotExistingTagThenReturnException() {
    assertThrows(RepositoryDeleteException.class, () -> tagRepository.delete(8L));
  }

  @Test
  void whenUpdateTagThenReturnException() {
    assertThrows(UnsupportedOperationException.class, () -> tagRepository.update(tag));
  }


  @Test
  void whenSaveTagCorrectThenReturnId() throws RepositorySaveException {
    Tag tag = new Tag(null, "tag");
    assertEquals(tag, tagRepository.save(tag));
  }

  @Test
  void whenGetTagsByCertificateIdThenReturnCorrectOneTag() {
    Set<Tag> tagsByCertificateId = tagRepository.getTagsByCertificateId(1L, 10, 0);
    assertEquals(1L, tagsByCertificateId.size());
  }

  @Test
  void whenGetTagByNameThenReturnCorrectTag() {
    Optional<Tag> tagByName = tagRepository.getTagByName("name");
    assertTrue(tagByName.isPresent());
    Tag tag = tagByName.get();
    assertEquals(1L, tag.getId());
  }

  @Test
  void whenGetTagByNotExistingNameThenReturnOptionalEmpty() {
    Optional<Tag> tagByName = tagRepository.getTagByName("ngg");
    assertFalse(tagByName.isPresent());
  }

  @Test
  void whenGetTheMostWidelyUsedTagThenReturnCorrectTag() {
    Tag widelyUsedTag = tagRepository.getTheMostWidelyUsedTag();
    assertEquals("name", widelyUsedTag.getName());
  }
}