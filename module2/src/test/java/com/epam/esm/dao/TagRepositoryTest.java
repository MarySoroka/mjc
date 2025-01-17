package com.epam.esm.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.esm.dao.impl.TagRepositoryImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import java.util.List;
import java.util.Optional;
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
    assertTrue(tagRepository.getById(3L).isPresent());
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
    List<Tag> tags = tagRepository.getAll();
    assertEquals(5, tags.size());
  }

  @Test
  void whenDeleteExistingTagThenReturnTrue() throws RepositoryDeleteException {
    tagRepository.delete(1L);
    assertDoesNotThrow(() -> tagRepository.getById(1L));
  }

  @Test
  void whenDeleteNotExistingTagThenReturnFalse() {
    assertThrows(RepositoryDeleteException.class, () -> tagRepository.delete(8L));
  }

  @Test
  void whenSaveTagCorrectThenReturnId() throws RepositorySaveException {
    Tag tag = new Tag(null, "tag");
    assertEquals(6L, tagRepository.save(tag));
  }
}