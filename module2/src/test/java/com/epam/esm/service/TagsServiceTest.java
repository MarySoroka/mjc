package com.epam.esm.service;


import com.epam.esm.dao.TagsDao;
import com.epam.esm.dao.TagsDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.exception.TagSaveException;
import com.epam.esm.exception.TagServiceException;
import com.epam.esm.mapper.TagRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TagsServiceTest {

    @BeforeEach
    public void setup() {
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .generateUniqueName(true)
                .addScript("classpath:database.sql")
                .addScript("classpath:test-data.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(dataSource);
        tagsDao = Mockito.spy(new TagsDaoImpl(jdbcTemplate, new TagRowMapper()));
        tagsService = new TagsServiceImpl(tagsDao);
    }

    @Mock
    JdbcTemplate jdbcTemplate;
    @Mock
    DataSource dataSource;
    @Mock
    TagsDao tagsDao;

    private TagsService tagsService;

    @Test
    void whenMockGetAllTags_thenReturnEmptyList() {
        Mockito.when(tagsDao.getAll()).thenReturn(new LinkedList<>());
        assertEquals(0, tagsService.getAllTags().size());
    }

    @Test
    void whenMockGetTagById_thenReturnTag() throws TagNotFoundException {
        Mockito.when(tagsDao.getById(Mockito.anyLong())).thenReturn(java.util.Optional.of(new Tag()));
        assertNotNull(tagsService.getTagById(1L));
    }

    @Test
    void whenMockCreateTag_thenReturnId() throws TagSaveException, TagServiceException {
        Tag tag = new Tag(1L, "name");
        Mockito.when(tagsDao.save(tag)).thenReturn(1L);
        tagsService.createTag(tag);
        assertEquals(1L, tag.getId());
    }

    @Test
    void whenMockDeleteTag_thenReturnTrue() throws TagServiceException {
        Mockito.when(tagsDao.delete(Mockito.anyLong())).thenReturn(true);
        tagsService.deleteTag(1L);
        Mockito.verify(tagsDao).delete(Mockito.anyLong());
    }
}