package com.epam.esm.mapper;

import com.epam.esm.dao.TagsDao;
import com.epam.esm.dao.TagsDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TagRowMapperTest {

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
    void mapRow() {
        Optional<Tag> tagsDaoById = tagsDao.getById(1L);
        assertTrue(tagsDaoById.isPresent());
        assertEquals("name",tagsDaoById.get().getTagName());
        assertEquals(1L,tagsDaoById.get().getId());

    }
}