package com.epam.esm.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.esm.dao.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.service.ServiceUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

class GiftCertificateRepositoryTest {

  private static GiftCertificateRepository giftCertificateRepository;

  private final GiftCertificate giftCertificate = new GiftCertificate(1L, "lala", "desk",
      new BigDecimal(12),
      ServiceUtils.getCurrentDateTime(), ServiceUtils.getCurrentDateTime(), 13);

  @BeforeAll
  public static void setup() {
    DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
        .generateUniqueName(true)
        .addScript("classpath:database.sql")
        .addScript("classpath:test-data.sql")
        .build();
    NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    giftCertificateRepository = new GiftCertificateRepositoryImpl(jdbcTemplate);
  }

  @Test
  void whenGetByIdExistingCertificateFromDatabaseThenReturnCorrectCertificate() {
    Optional<GiftCertificate> giftCertificatesDaoById = giftCertificateRepository.getById(2L);
    assertTrue(giftCertificatesDaoById.isPresent());
    GiftCertificate certificate = giftCertificatesDaoById.get();
    assertEquals("lala", certificate.getName());
  }

  @Test
  void whenGetAllCertificatesThenReturnOneCertificate() {
    List<GiftCertificate> giftCertificatesDaoAll = giftCertificateRepository.getAll();
    assertEquals(3L, giftCertificatesDaoAll.size());
  }

  @Test
  void whenDeleteNotExistingCertificateThenReturnFalse() {
    assertThrows(RepositoryDeleteException.class, () -> giftCertificateRepository.delete(5L));
  }

  @Test
  void whenUpdateExistingCertificateThenReturnTrue() {

    assertDoesNotThrow(() -> giftCertificateRepository.update(giftCertificate));

  }

  @Test
  void whenSaveCorrectCertificateEntityThenReturnGeneratedId() throws RepositorySaveException {
    assertTrue(giftCertificateRepository.save(giftCertificate) > 0);
  }
}