package com.epam.esm.service;


import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.dto.GiftCertificateDTO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.EntityValidationException;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {


  @Mock
  GiftCertificateRepository giftCertificateRepository;
  @InjectMocks
  GiftCertificateServiceImpl giftCertificatesService;
  GiftCertificate expectedGiftCertificate = new GiftCertificate(1L, "lala", "desk",
      new BigDecimal(12),
      ServiceUtils.getCurrentDateTime(), ServiceUtils.getCurrentDateTime(), 13);
  GiftCertificate giftCertificate = new GiftCertificate(1L, "lala1", "desk",
      new BigDecimal(13),
      ServiceUtils.getCurrentDateTime(), ServiceUtils.getCurrentDateTime(), 13);
  Tag tag = new Tag(1L, "name");

  @Test
  void whenMockGetAllExistingCertificatesThenReturnOneCertificate() {

    when(giftCertificateRepository.getAllByQuery(anyMap()))
        .thenReturn(Collections.singletonList(expectedGiftCertificate));

    List<GiftCertificate> certificates = giftCertificatesService
        .getAllCertificates(new HashMap<>());

    assertEquals(1, certificates.size());
    GiftCertificate giftCertificate = certificates.get(0);
    assertEquals(expectedGiftCertificate, giftCertificate);
    assertEquals(expectedGiftCertificate.getTags(), giftCertificate.getTags());
  }

  @Test
  void whenMockGetExistingCertificateByIdThenReturnCorrectCertificate() throws EntityNotFoundException {

    when(giftCertificateRepository.getById(anyLong()))
        .thenReturn(ofNullable(expectedGiftCertificate));

    GiftCertificate certificateById = giftCertificatesService.getCertificateById(1L);

    assertEquals(expectedGiftCertificate, certificateById);
    assertEquals(expectedGiftCertificate.getTags(), certificateById.getTags());
  }

  @Test
  void whenMockCreateCertificateCorrectThenReturnId() throws RepositorySaveException {
    expectedGiftCertificate.setTags(Collections.singleton(tag));
    when(giftCertificateRepository.save(any())).thenReturn(expectedGiftCertificate);

    assertEquals(expectedGiftCertificate,
        giftCertificatesService.createCertificate(new GiftCertificateDTO(expectedGiftCertificate)));
  }


  @Test
  void whenMockDeleteExistingCertificateThenDontThrowException() throws RepositoryDeleteException {
    doNothing().when(giftCertificateRepository).delete(anyLong());
    assertDoesNotThrow(() -> giftCertificatesService.deleteCertificate(1L));
  }

  @Test
  void whenMockUpdateExistingCertificateWithSameParamsThenThrowsException() {
    when(giftCertificateRepository.getById(anyLong())).thenReturn(ofNullable(
        expectedGiftCertificate));
    GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(expectedGiftCertificate);
    assertThrows(EntityValidationException.class,
        () -> giftCertificatesService
            .updateCertificate(giftCertificateDTO));
  }

  @Test
  void whenMockUpdateExistingCertificateWithNotSameParamsThenDontThrowException() {
    when(giftCertificateRepository.getById(anyLong())).thenReturn(ofNullable(
        expectedGiftCertificate));
    assertDoesNotThrow(
        () -> giftCertificatesService.updateCertificate(new GiftCertificateDTO(giftCertificate)));
  }

  @Test
  void whenMockUpdateExistingCertificateWithIncorrectParamsThenDontThrowException() {
    when(giftCertificateRepository.getById(anyLong())).thenReturn(ofNullable(
        expectedGiftCertificate));
    GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(expectedGiftCertificate);
    assertThrows(EntityValidationException.class,
        () -> giftCertificatesService
            .updateCertificate(giftCertificateDTO));
  }

  @Test
  void whenMockGetCertificateByExistingTagNameThenReturnCertificate() {
    when(
        giftCertificateRepository.getGiftCertificatesByTagName(Mockito.anyString(), anyInt(), anyInt()))
        .thenReturn(
            Collections.singletonList(giftCertificate));
    assertEquals(1L, giftCertificatesService.getCertificateByTagName("name", 10, 0).size());
  }
}