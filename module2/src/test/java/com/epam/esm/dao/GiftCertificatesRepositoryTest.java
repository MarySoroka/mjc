package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DaoSaveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificatesRepositoryTest {

    private GiftCertificatesRepository giftCertificatesRepository;

    private final GiftCertificate giftCertificate = new GiftCertificate(1L, "lala", "desk", new BigDecimal(12), LocalDate.now(), LocalDate.now(), 13);

    @BeforeEach
    public void setup() {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .generateUniqueName(true)
                .addScript("classpath:database.sql")
                .addScript("classpath:test-data.sql")
                .build();
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        giftCertificatesRepository = new GiftCertificatesRepositoryImpl(jdbcTemplate);
    }

    //Test date
    @Test
    void whenGetByIdExistingCertificateFromDatabase_thenReturnCorrectCertificate() {
        Optional<GiftCertificate> giftCertificatesDaoById = giftCertificatesRepository.getById(1L);
        assertTrue(giftCertificatesDaoById.isPresent());
        assertEquals("desc", giftCertificatesDaoById.get().getDescription());
        assertEquals("lala", giftCertificatesDaoById.get().getName());
        assertEquals(new BigDecimal(12), giftCertificatesDaoById.get().getPrice());
    }

    @Test
    void whenGetAllCertificates_thenReturnOneCertificate() {
        List<GiftCertificate> giftCertificatesDaoAll = giftCertificatesRepository.getAll();
        assertEquals(1L, giftCertificatesDaoAll.size());
    }

    @Test
    void whenDeleteExistingCertificate_thenReturnTrue() {
        assertTrue(giftCertificatesRepository.delete(1L));
    }

    @Test
    void whenDeleteNotExistingCertificate_thenReturnFalse() {
        assertFalse(giftCertificatesRepository.delete(2L));
    }

    @Test
    void whenUpdateExistingCertificate_thenReturnTrue() {
        assertTrue(giftCertificatesRepository.update(giftCertificate));

    }

    @Test
    void whenSaveCorrectCertificateEntity_thenReturnGeneratedId() throws DaoSaveException {
        assertTrue(giftCertificatesRepository.save(giftCertificate) > 0);
    }
}