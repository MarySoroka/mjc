package com.epam.esm.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.esm.dao.impl.TagRepositoryImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

class TagRepositoryTest {

  private static TagRepository tagRepository;

  @BeforeAll
  public static void setup() {
    DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
        .generateUniqueName(true)
        .addScript("classpath:database.sql")
        .addScript("classpath:test-data.sql")
        .build();
    NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
        dataSource);
    tagRepository = new TagRepositoryImpl(namedParameterJdbcTemplate);

  }

  @Test
  void whenGetByIdExistingTagFromDatabaseThenReturnCorrectTag() {
    Optional<Tag> tagsRepositoryById = tagRepository.getById(3L);
    assertTrue(tagsRepositoryById.isPresent());
    Tag tag = tagsRepositoryById.get();
    assertEquals("la3", tag.getName());
  }

  @Test
  void whenGetByIdNotExistingTagFromDatabaseThenReturnOptionalEmpty() {
    Optional<Tag> tagsRepositoryById = tagRepository.getById(10L);
    assertFalse(tagsRepositoryById.isPresent());
  }

  @Test
  void whenGetAllFromDatabaseThenReturnCorrectTagsCount() {
    List<Tag> tags = tagRepository.getAll(10,0);
    assertEquals(5, tags.size());
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
    Tag tag = new Tag();
    assertThrows(UnsupportedOperationException.class, () ->
        tagRepository.update(tag)
    );
  }


  @Test
  void whenSaveTagCorrectThenReturnId() throws RepositorySaveException {
    Tag tag = new Tag(null, "tag");
    assertEquals(6L, tagRepository.save(tag));
  }

  @Test
  void whenGetTagsByCertificateIdThenReturnCorrectTwoTags() {
    Set<Tag> tagsByCertificateId = tagRepository.getTagsByCertificateId(1L);
    assertEquals(1L, tagsByCertificateId.size());
  }

  @Test
  void whenGetTagByNameThenReturnCorrectTag() {
    Optional<Tag> tagByName = tagRepository.getTagByName("name");
    assertTrue( tagByName.isPresent());
    Tag tag = tagByName.get();
    assertEquals(1L, tag.getId());
  }
  @Test
  void whenGetTagByNotExistingNameThenReturnOptionalEmpty() {
    Optional<Tag> tagByName = tagRepository.getTagByName("ngg");
    assertFalse( tagByName.isPresent());
  }

  @Test
  void whenSaveCertificateTagThenDoesntThrowsException() {
    assertDoesNotThrow(() -> tagRepository.saveCertificateTag(4L, 3L));
  }

  @Test
  void whenDeleteExistingCertificateTagThenReturnTrue() throws RepositoryDeleteException {
    tagRepository.deleteCertificateTag(1L,1L);
    Set<Tag> tagsByCertificateId = tagRepository.getTagsByCertificateId(1L);
    assertNotEquals(2L,tagsByCertificateId.size());
  }

  @Test
  void whenDeleteNotExistingCertificateTagThenReturnException() {
    assertThrows(RepositoryDeleteException.class, () -> tagRepository.deleteCertificateTag(100L,10L));
  }
  @Test
  void whenGetTheMostWidelyUsedTagThenReturnCorrectTag() {
    Tag widelyUsedTag = tagRepository.getTheMostWidelyUsedTag();
    assertEquals("name", widelyUsedTag.getName());
  }
}