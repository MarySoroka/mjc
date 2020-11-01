package com.epam.esm.service;


import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.epam.esm.dao.GiftCertificatesRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.exception.RepositoryUpdateException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GiftCertificatesServiceTest {


  @Mock
  GiftCertificatesRepository giftCertificatesRepository;
  @Mock
  TagsService tagsService;
  @InjectMocks
  GiftCertificatesServiceImpl giftCertificatesService;

  GiftCertificate giftCertificate = new GiftCertificate(1L, "lala", "desk", new BigDecimal(12),
      ServiceUtils.getCurrentDateTime(), ServiceUtils.getCurrentDateTime(), 13);


  @Test
  void whenGetAllExistingCertificatesThenReturnOneCertificate() {
    List<GiftCertificate> certificates = giftCertificatesService
        .getAllCertificates(new HashMap<>());
    int size = certificates.size();
    assertEquals(0, size);
  }

  @Test
  void whenGetExistingCertificateByIdThenReturnCorrectCertificate()
      throws GiftCertificateNotFoundException {
    when(giftCertificatesRepository.getById(anyLong()))
        .thenReturn(ofNullable(giftCertificate));
    GiftCertificate certificateById = giftCertificatesService.getCertificateById(1L);
    assertEquals(1L, certificateById.getId());
    assertEquals("lala", certificateById.getName());

  }

  @Test
  void whenCreateCertificateCorrectThenReturnId()
      throws RepositorySaveException {
    when(giftCertificatesRepository.save(any())).thenReturn(1L);
    ThrowingSupplier<Long> supplier = () -> giftCertificatesService
        .createCertificate(giftCertificate);
    assertDoesNotThrow(supplier);
  }

  @Test
  void whenDeleteExistingCertificateThenReturnTrue() throws RepositoryDeleteException {
    doNothing().when(giftCertificatesRepository).delete(anyLong());
    Executable executable = () -> giftCertificatesService.deleteCertificate(1L);
    assertDoesNotThrow(executable);
  }

  @Test
  void whenUpdateExistingCertificateThenReturnTrue() {

    when(giftCertificatesRepository.getById(anyLong())).thenReturn(ofNullable(giftCertificate));
    Executable executable = () -> giftCertificatesService.updateCertificate(giftCertificate);
    assertDoesNotThrow(executable);
  }


}