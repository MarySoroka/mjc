package com.epam.esm.mapper;

import com.epam.esm.dao.GiftCertificatesDao;
import com.epam.esm.dao.GiftCertificatesDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateRowMapperTest {
    @Mock
    JdbcTemplate jdbcTemplate;

    DataSource dataSource;
    GiftCertificatesDao giftCertificatesDao;

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
    @Test
    void mapRow() {
        Optional<GiftCertificate> giftCertificatesDaoById = giftCertificatesDao.getById(1L);
        assertTrue(giftCertificatesDaoById.isPresent());
        assertEquals("lala", giftCertificatesDaoById.get().getCertificateName());
        assertEquals("desc", giftCertificatesDaoById.get().getCertificateDescription());
    }
}