package com.epam.esm.tag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class TagsDaoTest {
    @Mock
    JdbcTemplate jdbcTemplate;

    DataSource dataSource;
    TagsDao tagsDao;

    @BeforeEach
    public void setup() {
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .generateUniqueName(true)
                .addScript("classpath:database.sql")
                .addScript("classpath:test-data.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(dataSource);
        tagsDao = new TagsDaoImpl(jdbcTemplate,new TagRowMapper());
    }

    @Test
    void whenGetByIdExistingTagFromDatabase_thenReturnCorrectTag() {
       assertTrue(tagsDao.getById(3L).isPresent());
       assertEquals("la3",tagsDao.getById(3L).get().getTagName());
    }
    @Test
    void whenGetByIdNotExistingTagFromDatabase_thenReturnOptionalEmpty() {
        assertFalse(tagsDao.getById(7L).isPresent());
    }

    @Test
    void whenGetAllFromDatabase_thenReturnCorrectTagsCount() {
        assertEquals(5, tagsDao.getAll().size());
    }

    @Test
    void whenDeleteExistingTag_thenReturnTrue() {
        assertTrue(tagsDao.delete(1L));
    }
    @Test
    void whenDeleteNotExistingTag_thenReturnFalse() {
        assertFalse(tagsDao.delete(8L));
    }

    @Test
    void whenSaveTagCorrect_thenReturnId() throws TagSaveException {
        Tag tag = new Tag(6L, "tag");
        assertEquals(6L, tagsDao.save(tag));
    }
}