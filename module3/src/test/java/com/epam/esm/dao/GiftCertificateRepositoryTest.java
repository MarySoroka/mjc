package com.epam.esm.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.service.ServiceUtils;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class GiftCertificateRepositoryTest {

  private static final Map<String, String> queryParams = new HashMap<>(3);
  private static final GiftCertificate giftCertificate = new GiftCertificate(null, "name3", "desk",
      new BigDecimal(12),
      ServiceUtils.getCurrentDateTime(), ServiceUtils.getCurrentDateTime(), 13);
  private final GiftCertificate giftCertificate2 = new GiftCertificate(null, "name4", "desk2",
      new BigDecimal(18),
      ServiceUtils.getCurrentDateTime(), ServiceUtils.getCurrentDateTime(), 13);

  private final GiftCertificate notExistingGiftCertificate = new GiftCertificate(100L, "lala",
      "desc",
      new BigDecimal(12),
      ServiceUtils.getCurrentDateTime(), ServiceUtils.getCurrentDateTime(), 13);


  @Autowired
  private GiftCertificateRepository giftCertificateRepository;

  @BeforeAll
  static void setUp() {
    queryParams.put("name", "name3");
    queryParams.put("limit", "10");
    queryParams.put("offset", "0");
    giftCertificate.setTags(Collections.singleton(new Tag(null, "name")));
  }

  @Test
  void whenGetByIdExistingCertificateFromDatabaseThenReturnCorrectCertificate() {
    Optional<GiftCertificate> giftCertificatesDaoById = giftCertificateRepository.getById(giftCertificate.getId());
    assertTrue(giftCertificatesDaoById.isPresent());
    GiftCertificate certificate = giftCertificatesDaoById.get();
    assertEquals(giftCertificate.getName(), certificate.getName());
  }

  @Test
  void whenGetAllCertificatesThenReturnTwoCertificates() {
    List<GiftCertificate> giftCertificatesDaoAll = giftCertificateRepository.getAll(10, 0);
    assertEquals(2L, giftCertificatesDaoAll.size());
  }

  @Test
  void whenDeleteNotExistingCertificateThenThrowsRepositoryDeleteException() {
    assertThrows(RepositoryDeleteException.class, () -> giftCertificateRepository.delete(6L));
  }

  @Test
  void whenUpdateExistingCertificateThenDontThrowsException() {
    giftCertificate2.setId(null);
    giftCertificate2.setName("name45");

    assertDoesNotThrow(() -> giftCertificateRepository.update(giftCertificate2));

  }


  @Test
  void whenSaveCorrectCertificateEntityThenReturnGeneratedId() throws RepositorySaveException {
    assertEquals(giftCertificate2, giftCertificateRepository.save(giftCertificate2));
  }

  @Test
  void whenGetAllCertificatesByQueryThenReturnCorrectOneQuery() throws RepositorySaveException {
    giftCertificateRepository.save(giftCertificate);
    List<GiftCertificate> allByQuery = giftCertificateRepository.getAllByQuery(queryParams);
    assertEquals(1L, allByQuery.size());
  }

}