package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificatesDao;
import com.epam.esm.dao.GiftCertificatesDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateSaveException;
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
    GiftCertificatesDao giftCertificatesDao;

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
        giftCertificatesDao = Mockito.spy(new GiftCertificatesDaoImpl(jdbcTemplate, new GiftCertificateRowMapper()));
        giftCertificatesService = new GiftCertificatesServiceImpl(giftCertificatesDao);
    }
    @Test
    void whenGetAllExistingCertificates_thenReturnOneCertificate() {
        Mockito.when(giftCertificatesDao.getAll()).thenReturn(new LinkedList<>());
        assertEquals(0, giftCertificatesService.getAllCertificates().size());
    }

    @Test
    void whenGetExistingCertificateById_thenReturnCorrectCertificate() throws GiftCertificateServiceException {
        Mockito.when(giftCertificatesDao.getById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(giftCertificate));
        GiftCertificate certificateById = giftCertificatesService.getCertificateById(1L);
        assertEquals(1L, certificateById.getId());
        assertEquals("lala",certificateById.getCertificateName());

    }

    @Test
    void whenCreateCertificateCorrect_thenReturnId() throws GiftCertificateSaveException, GiftCertificateServiceException {
        Mockito.when(giftCertificatesDao.save(giftCertificate)).thenReturn(1L);
        assertTrue(giftCertificatesService.createCertificate(giftCertificate));
    }

    @Test
    void whenDeleteExistingCertificate_thenReturnTrue() {
        Mockito.when(giftCertificatesDao.delete(Mockito.anyLong())).thenReturn(true);
        assertTrue(giftCertificatesService.deleteCertificate(1L));
    }

    @Test
    void whenUpdateExistingCertificate_thenReturnTrue() {
        Mockito.when(giftCertificatesDao.update(Mockito.any(GiftCertificate.class))).thenReturn(true);
        assertTrue(giftCertificatesService.updateCertificate(giftCertificate));
    }


    @Test
    void whenGetCertificateByTagName() {
    }
}