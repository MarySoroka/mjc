package com.epam.esm.service;


import com.epam.esm.dao.TagsRepository;
import com.epam.esm.dao.TagsRepositoryImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoSaveException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.exception.TagServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        tagsRepository = Mockito.spy(new TagsRepositoryImpl(namedParameterJdbcTemplate));
        tagsService = new TagsServiceImpl(tagsRepository);
    }

    @Mock
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Mock
    DataSource dataSource;
    @Mock
    TagsRepository tagsRepository;

    private TagsService tagsService;

    @Test
    void whenMockGetAllTags_thenReturnEmptyList() {
        Mockito.when(tagsRepository.getAll()).thenReturn(new LinkedList<>());
        assertEquals(0, tagsService.getAllTags().size());
    }

    @Test
    void whenMockGetTagById_thenReturnTag() throws TagNotFoundException {
        Mockito.when(tagsRepository.getById(Mockito.anyLong())).thenReturn(java.util.Optional.of(new Tag()));
        assertNotNull(tagsService.getTagById(1L));
    }

    @Test
    void whenMockCreateTag_thenReturnId() throws DaoSaveException, TagServiceException {
        Tag tag = new Tag(1L, "name");
        Mockito.when(tagsRepository.save(tag)).thenReturn(1L);
        tagsService.createTag(tag);
        assertEquals(1L, tag.getId());
    }

    @Test
    void whenMockDeleteTag_thenReturnTrue() throws TagServiceException {
        Mockito.when(tagsRepository.delete(Mockito.anyLong())).thenReturn(true);
        tagsService.deleteTag(1L);
        Mockito.verify(tagsRepository).delete(Mockito.anyLong());
    }
}