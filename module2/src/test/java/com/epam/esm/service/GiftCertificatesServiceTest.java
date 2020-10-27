package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificatesRepository;
import com.epam.esm.dao.GiftCertificatesRepositoryImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DaoSaveException;
import com.epam.esm.exception.GiftCertificateServiceException;
import com.epam.esm.mapper.GiftCertificateRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificatesServiceTest {
    @Mock
    JdbcTemplate jdbcTemplate;
    @Mock
    DataSource dataSource;
    @Mock
    GiftCertificatesRepository giftCertificatesRepository;

    GiftCertificatesService giftCertificatesService;

    GiftCertificate giftCertificate = new GiftCertificate(1L, "lala", "desk", new BigDecimal(12), LocalDate.now(), LocalDate.now(), 13);

    @BeforeEach
    public void setup() {
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .generateUniqueName(true)
                .addScript("classpath:database.sql")
                .addScript("classpath:test-data.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(dataSource);
        giftCertificatesRepository = Mockito.spy(new GiftCertificatesRepositoryImpl(jdbcTemplate, new GiftCertificateRowMapper()));
        giftCertificatesService = new GiftCertificatesServiceImpl(giftCertificatesRepository);
    }
    @Test
    void whenGetAllExistingCertificates_thenReturnOneCertificate() {
        Mockito.when(giftCertificatesRepository.getAll()).thenReturn(new LinkedList<>());
        assertEquals(0, giftCertificatesService.getAllCertificates().size());
    }

    @Test
    void whenGetExistingCertificateById_thenReturnCorrectCertificate() throws GiftCertificateServiceException {
        Mockito.when(giftCertificatesRepository.getById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(giftCertificate));
        GiftCertificate certificateById = giftCertificatesService.getCertificateById(1L);
        assertEquals(1L, certificateById.getId());
        assertEquals("lala",certificateById.getCertificateName());

    }

    @Test
    void whenCreateCertificateCorrect_thenReturnId() throws DaoSaveException, GiftCertificateServiceException {
        Mockito.when(giftCertificatesRepository.save(giftCertificate)).thenReturn(1L);
        assertTrue(giftCertificatesService.createCertificate(giftCertificate));
    }

    @Test
    void whenDeleteExistingCertificate_thenReturnTrue() {
        Mockito.when(giftCertificatesRepository.delete(Mockito.anyLong())).thenReturn(true);
        assertTrue(giftCertificatesService.deleteCertificate(1L));
    }

    @Test
    void whenUpdateExistingCertificate_thenReturnTrue() {
        Mockito.when(giftCertificatesRepository.update(Mockito.any(GiftCertificate.class))).thenReturn(true);
        assertTrue(giftCertificatesService.updateCertificate(giftCertificate));
    }


    @Test
    void whenGetCertificateByTagName() {
    }
}