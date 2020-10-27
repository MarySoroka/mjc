package com.epam.esm.dao;

import com.epam.esm.dao.GiftCertificatesDao;
import com.epam.esm.dao.GiftCertificatesDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateSaveException;
import com.epam.esm.mapper.GiftCertificateRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificatesDaoTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    DataSource dataSource;
    GiftCertificatesDao giftCertificatesDao;

    GiftCertificate giftCertificate = new GiftCertificate(1L, "lala", "desk", new BigDecimal(12), LocalDate.now(), LocalDate.now(), 13);

    @BeforeEach
    public void setup() {
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .generateUniqueName(true)
                .addScript("classpath:database.sql")
                .addScript("classpath:test-data.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(dataSource);
        giftCertificatesDao = new GiftCertificatesDaoImpl(jdbcTemplate, new GiftCertificateRowMapper());
    }

    //Test date
    @Test
    void whenGetByIdExistingCertificateFromDatabase_thenReturnCorrectCertificate() {
        Optional<GiftCertificate> giftCertificatesDaoById = giftCertificatesDao.getById(1L);
        assertTrue(giftCertificatesDaoById.isPresent());
        assertEquals("desc", giftCertificatesDaoById.get().getCertificateDescription());
        assertEquals("lala", giftCertificatesDaoById.get().getCertificateName());
        assertEquals(new BigDecimal(12), giftCertificatesDaoById.get().getCertificatePrice());
    }

    @Test
    void whenGetAllCertificates_thenReturnOneCertificate() {
        List<GiftCertificate> giftCertificatesDaoAll = giftCertificatesDao.getAll();
        assertEquals(1L, giftCertificatesDaoAll.size());
    }

    @Test
    void whenDeleteExistingCertificate_thenReturnTrue() {
        assertTrue(giftCertificatesDao.delete(1L));
    }

    @Test
    void whenDeleteNotExistingCertificate_thenReturnFalse() {
        assertFalse(giftCertificatesDao.delete(2L));
    }

    @Test
    void whenUpdateExistingCertificate_thenReturnTrue() {
        assertTrue(giftCertificatesDao.update(giftCertificate));

    }

    @Test
    void whenSaveCorrectCertificateEntity_thenReturnGeneratedId() throws GiftCertificateSaveException {
        assertTrue(giftCertificatesDao.save(giftCertificate)>0);
    }
}