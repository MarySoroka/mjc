package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoSaveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TagsRepositoryTest {

    private TagsRepository tagsRepository;

    @BeforeEach
    public void setup() {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .generateUniqueName(true)
                .addScript("classpath:database.sql")
                .addScript("classpath:test-data.sql")
                .build();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        tagsRepository = new TagsRepositoryImpl(namedParameterJdbcTemplate);
    }

    @Test
    void whenGetByIdExistingTagFromDatabase_thenReturnCorrectTag() {
       assertTrue(tagsRepository.getById(3L).isPresent());
       assertEquals("la3", tagsRepository.getById(3L).get().getName());
    }
    @Test
    void whenGetByIdNotExistingTagFromDatabase_thenReturnOptionalEmpty() {
        Optional<Tag> tagsRepositoryById = tagsRepository.getById(10L);
        assertFalse(tagsRepositoryById.isPresent());
    }

    @Test
    void whenGetAllFromDatabase_thenReturnCorrectTagsCount() {
        assertEquals(5, tagsRepository.getAll().size());
    }

    @Test
    void whenDeleteExistingTag_thenReturnTrue() {
        assertTrue(tagsRepository.delete(1L));
    }
    @Test
    void whenDeleteNotExistingTag_thenReturnFalse() {
        assertFalse(tagsRepository.delete(8L));
    }

    @Test
    void whenSaveTagCorrect_thenReturnId() throws DaoSaveException {
        Tag tag = new Tag(6L, "tag");
        assertEquals(6L, tagsRepository.save(tag));
    }
}