package com.epam.esm.service;


import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.GiftCertificateServiceException;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.exception.TagServiceException;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {


  @Mock
  GiftCertificateRepository giftCertificateRepository;
  @Mock
  TagService tagService;
  @InjectMocks
  GiftCertificateServiceImpl giftCertificatesService;

  GiftCertificate expectedGiftCertificate = new GiftCertificate(1L, "lala", "desk",
      new BigDecimal(12),
      ServiceUtils.getCurrentDateTime(), ServiceUtils.getCurrentDateTime(), 13);
  Tag tag = new Tag(1L, "name");


  @Test
  void whenGetAllExistingCertificatesThenReturnOneCertificate() {
    expectedGiftCertificate.setTags(Collections.singleton(tag));

    when(giftCertificateRepository.getAllByQuery(anyMap()))
        .thenReturn(Collections.singletonList(expectedGiftCertificate));
    when(tagService.getTagsByCertificateId(anyLong())).thenReturn(Collections.singleton(tag));

    List<GiftCertificate> certificates = giftCertificatesService
        .getAllCertificates(new HashMap<>());

    assertEquals(1, certificates.size());
    GiftCertificate giftCertificate = certificates.get(0);
    assertEquals(expectedGiftCertificate, giftCertificate);
    assertEquals(expectedGiftCertificate.getTags(), giftCertificate.getTags());
  }

  @Test
  void whenGetExistingCertificateByIdThenReturnCorrectCertificate()
      throws EntityNotFoundException {
    expectedGiftCertificate.setTags(Collections.singleton(tag));

    when(giftCertificateRepository.getById(anyLong()))
        .thenReturn(ofNullable(expectedGiftCertificate));
    when(tagService.getTagsByCertificateId(anyLong())).thenReturn(Collections.singleton(tag));

    GiftCertificate certificateById = giftCertificatesService.getCertificateById(1L);

    assertEquals(expectedGiftCertificate, certificateById);
    assertEquals(expectedGiftCertificate.getTags(), certificateById.getTags());
  }

  @Test
  void whenCreateCertificateCorrectThenReturnId()
      throws RepositorySaveException, TagServiceException, EntityNotFoundException {
    expectedGiftCertificate.setTags(Collections.singleton(tag));
    when(giftCertificateRepository.save(any())).thenReturn(1L);
    doNothing().when(tagService).saveCertificateTag(any(Tag.class), anyLong());
    when(giftCertificateRepository.getById(anyLong())).thenReturn(ofNullable(
        expectedGiftCertificate));

    assertEquals(expectedGiftCertificate,
        giftCertificatesService.createCertificate(expectedGiftCertificate));
  }

  @Test
  void whenDeleteExistingCertificateThenDontThrowException() throws RepositoryDeleteException {
    doNothing().when(giftCertificateRepository).delete(anyLong());
    assertDoesNotThrow(() -> giftCertificatesService.deleteCertificate(1L));
  }

  @Test
  void whenUpdateExistingCertificateThenReturnDontThrowException() {
    when(giftCertificateRepository.getById(anyLong())).thenReturn(ofNullable(
        expectedGiftCertificate));

    assertDoesNotThrow(() -> giftCertificatesService.updateCertificate(expectedGiftCertificate));
  }


}