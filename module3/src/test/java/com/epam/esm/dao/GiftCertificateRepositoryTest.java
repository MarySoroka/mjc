package com.epam.esm.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.esm.dao.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.exception.RepositoryUpdateException;
import com.epam.esm.service.ServiceUtils;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

class
GiftCertificateRepositoryTest {

  private static GiftCertificateRepository giftCertificateRepository;

  private final GiftCertificate giftCertificate = new GiftCertificate(1L, "name3", "desk",
      new BigDecimal(12),
      ServiceUtils.getCurrentDateTime(), ServiceUtils.getCurrentDateTime(), 13);
  private final GiftCertificate notExistingGiftCertificate = new GiftCertificate(100L, "lala",
      "desc",
      new BigDecimal(12),
      ServiceUtils.getCurrentDateTime(), ServiceUtils.getCurrentDateTime(), 13);
  private static final Map<String, String> queryParams = new HashMap<>(3 );

  @BeforeAll
  public static void setup() {
    DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
        .generateUniqueName(true)
        .addScript("classpath:database.sql")
        .addScript("classpath:test-data.sql")
        .build();
    NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    giftCertificateRepository = new GiftCertificateRepositoryImpl(jdbcTemplate);

    queryParams.put("limit", "10");
    queryParams.put("offset","0");
    queryParams.put("name","lala");

  }

  @Test
  void whenGetByIdExistingCertificateFromDatabaseThenReturnCorrectCertificate() {
    Optional<GiftCertificate> giftCertificatesDaoById = giftCertificateRepository.getById(2L);
    assertTrue(giftCertificatesDaoById.isPresent());
    GiftCertificate certificate = giftCertificatesDaoById.get();
    assertEquals(2L, certificate.getId());
    assertEquals("l2", certificate.getName());
  }

  @Test
  void whenGetExistingCertificateFromDatabaseByTagNameThenReturnCorrectCertificate() {
    List<GiftCertificate> giftCertificatesByTagName = giftCertificateRepository
        .getGiftCertificatesByTagName("name", 10,0);
    assertEquals(2L,giftCertificatesByTagName.size());
  }

  @Test
  void whenGetAllCertificatesThenReturnThreeCertificates() {
    List<GiftCertificate> giftCertificatesDaoAll = giftCertificateRepository
        .getAll(10,0);
    assertEquals(3L, giftCertificatesDaoAll.size());
  }

  @Test
  void whenDeleteNotExistingCertificateThenThrowsRepositoryDeleteException() {
    assertThrows(RepositoryDeleteException.class, () -> giftCertificateRepository.delete(6L));
  }

  @Test
  void whenUpdateExistingCertificateThenDontThrowsException() {
    assertDoesNotThrow(() -> giftCertificateRepository.update(giftCertificate));

  }

  @Test
  void whenUpdateNotExistingCertificateThenDontThrowsException() {
    assertThrows(RepositoryUpdateException.class,
        () -> giftCertificateRepository.update(notExistingGiftCertificate));

  }

  @Test
  void whenSaveCorrectCertificateEntityThenReturnGeneratedId() throws RepositorySaveException {
    assertTrue(giftCertificateRepository.save(giftCertificate) > 0);
  }

  @Test
  void whenGetAllCertificatesByQueryThenReturnCorrectOneQuery() {
    List<GiftCertificate> allByQuery = giftCertificateRepository.getAllByQuery(queryParams);
    assertEquals(1L, allByQuery.size());
  }

}