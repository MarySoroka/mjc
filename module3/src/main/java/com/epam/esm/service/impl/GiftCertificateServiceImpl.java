package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.dto.GiftCertificateDTO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.EntityValidationException;
import com.epam.esm.exception.GiftCertificateServiceException;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.exception.RepositoryUpdateException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.ServiceUtils;
import com.epam.esm.service.TagService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

  private final GiftCertificateRepository giftCertificateRepository;
  private final TagService tagService;

  @Autowired
  public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository,
      TagService tagService) {
    this.giftCertificateRepository = giftCertificateRepository;
    this.tagService = tagService;
  }

  @Override
  public List<GiftCertificate> getAllCertificates(Map<String, String> queryParams) {
    return giftCertificateRepository.getAllByQuery(queryParams);
  }

  @Override
  public GiftCertificate getCertificateById(Long id)
      throws EntityNotFoundException {
    Optional<GiftCertificate> giftCertificate = giftCertificateRepository.getById(id);
    if (!giftCertificate.isPresent()) {
      throw new EntityNotFoundException(
          "Service exception : Couldn't get certificate by id : " + id);
    }
    return giftCertificate.get();
  }

  @Override
  @Transactional
  public GiftCertificate createCertificate(GiftCertificateDTO giftCertificate)
      throws RepositorySaveException {
    LocalDateTime currentDateTime = ServiceUtils.getCurrentDateTime();
    giftCertificate.setLastUpdateDate(currentDateTime);
    giftCertificate.setCreateDate(currentDateTime);
    GiftCertificate certificate = new GiftCertificate(giftCertificate);
    return giftCertificateRepository.save(certificate);
  }

  @Override
  @Transactional
  public void deleteCertificate(Long certificateId)
      throws RepositoryDeleteException {
    giftCertificateRepository.delete(certificateId);
  }

  @Override
  @Transactional
  public void updateCertificate(GiftCertificateDTO giftCertificate)
      throws GiftCertificateServiceException {
    try {
      GiftCertificate updateCertificate = new GiftCertificate(giftCertificate);
      GiftCertificate certificateById = getCertificateById(giftCertificate.getId());
      if (!updateCertificate.equals(certificateById)) {
        updateCertificateFields(certificateById, updateCertificate);
        giftCertificateRepository.update(updateCertificate);
      } else {
        throw new EntityValidationException(
            "Service exception: no new entity field values was found");
      }
    } catch (RepositoryUpdateException | EntityNotFoundException e) {
      throw new GiftCertificateServiceException("Service exception : couldn't update certificate ",
          e);
    }
  }


  @Override
  public List<GiftCertificate> getCertificateByTagName(String tagName,
      Map<String, Integer> pagination) {
    return giftCertificateRepository
        .getGiftCertificatesByTagName(tagName, pagination);
  }

  private void updateCertificateFields(GiftCertificate certificate,
      GiftCertificate updateCertificate) {
    updateCertificate.setCreateDate(updateCertificate.getCreateDate() != null
        ? updateCertificate.getCreateDate()
        : certificate.getCreateDate());
    updateCertificate.setDescription(updateCertificate.getDescription() != null
        ? updateCertificate.getDescription()
        : certificate.getDescription());
    updateCertificate.setDuration(updateCertificate.getDuration() != null
        ? updateCertificate.getDuration()
        : certificate.getDuration());
    updateCertificate.setLastUpdateDate(ServiceUtils.getCurrentDateTime());
    updateCertificate.setPrice(updateCertificate.getPrice() != null
        ? updateCertificate.getPrice()
        : certificate.getPrice());
    BigDecimal price = updateCertificate.getPrice();
    if (price.compareTo(BigDecimal.ZERO) < 0 || updateCertificate.getDuration() < 0) {
      throw new EntityValidationException("Validation exception: invalid query values");
    }

  }
}
