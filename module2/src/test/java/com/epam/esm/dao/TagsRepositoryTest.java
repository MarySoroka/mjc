package com.epam.esm.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.exception.TagNotFoundException;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

class TagsRepositoryTest {

  private static TagsRepository tagsRepository;

  @BeforeAll
  public static void setup() {
    DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
        .generateUniqueName(true)
        .addScript("classpath:database.sql")
        .addScript("classpath:test-data.sql")
        .build();
    NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
        dataSource);
    tagsRepository = new TagsRepositoryImpl(namedParameterJdbcTemplate);
  }

  @Test
  void whenGetByIdExistingTagFromDatabaseThenReturnCorrectTag() {
    assertTrue(tagsRepository.getById(3L).isPresent());
    Optional<Tag> tagsRepositoryById = tagsRepository.getById(3L);
    assertTrue(tagsRepositoryById.isPresent());
    Tag tag = tagsRepositoryById.get();
    assertEquals("la3", tag.getName());
  }

  @Test
  void whenGetByIdNotExistingTagFromDatabaseThenReturnOptionalEmpty() {
    Optional<Tag> tagsRepositoryById = tagsRepository.getById(10L);
    assertFalse(tagsRepositoryById.isPresent());
  }

  @Test
  void whenGetAllFromDatabaseThenReturnCorrectTagsCount() {
    List<Tag> tags = tagsRepository.getAll();
    assertEquals(5, tags.size());
  }

  @Test
  void whenDeleteExistingTagThenReturnTrue() throws RepositoryDeleteException {
    tagsRepository.delete(1L);
    assertDoesNotThrow(() -> tagsRepository.getById(1L));
  }

  @Test
  void whenDeleteNotExistingTagThenReturnFalse() {
    assertThrows(RepositoryDeleteException.class, () -> tagsRepository.delete(8L));
  }

  @Test
  void whenSaveTagCorrectThenReturnId() throws RepositorySaveException {
    Tag tag = new Tag(null, "tag");
    assertEquals(6L, tagsRepository.save(tag));
  }
}