package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.GiftCertificateServiceException;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.exception.RepositoryUpdateException;
import com.epam.esm.exception.TagServiceException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.ServiceUtils;
import com.epam.esm.service.TagService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
    List<GiftCertificate> giftCertificates;
    giftCertificates = giftCertificateRepository.getAllByQuery(queryParams);
    giftCertificates.forEach(giftCertificate -> giftCertificate
        .setTags(new HashSet<>(tagService.getTagsByCertificateId(giftCertificate.getId()))));

    return giftCertificates;
  }

  @Override
  public GiftCertificate getCertificateById(Long id)
      throws EntityNotFoundException {
    Optional<GiftCertificate> giftCertificate = giftCertificateRepository.getById(id);
    if (!giftCertificate.isPresent()) {
      throw new EntityNotFoundException(
          "Service exception : Couldn't get certificate by id : " + id);
    }
    giftCertificate.get()
        .setTags(tagService.getTagsByCertificateId(giftCertificate.get().getId()));
    return giftCertificate.get();
  }

  @Override
  @Transactional
  public GiftCertificate createCertificate(GiftCertificate giftCertificate)
      throws GiftCertificateServiceException {
    try {
      LocalDateTime currentDateTime = ServiceUtils.getCurrentDateTime();
      giftCertificate.setLastUpdateDate(currentDateTime);
      giftCertificate.setCreateDate(currentDateTime);
      Long certificateId = giftCertificateRepository.save(giftCertificate);
      Set<Tag> tags = giftCertificate.getTags();
      if (tags != null && !tags.isEmpty()) {
        for (Tag tag : tags) {
          tagService.saveCertificateTag(tag, certificateId);
        }
      }
      return getCertificateById(certificateId);
    } catch (RepositorySaveException | EntityNotFoundException e) {
      throw new GiftCertificateServiceException("Service exception : Couldn't create certificate ");
    } catch (TagServiceException e) {
      throw new GiftCertificateServiceException(
          "Service exception : Couldn't create tag certificate ");
    }
  }

  @Override
  @Transactional
  public void deleteCertificate(Long certificateId) throws GiftCertificateServiceException {
    try {
      giftCertificateRepository.delete(certificateId);
    } catch (RepositoryDeleteException e) {
      throw new GiftCertificateServiceException(
          "Service exception : Couldn't delete certificate " + certificateId);
    }
  }

  @Override
  @Transactional
  public void updateCertificate(GiftCertificate giftCertificate)
      throws GiftCertificateServiceException {
    try {
      GiftCertificate certificateById = getCertificateById(giftCertificate.getId());
      if (!giftCertificate.equals(certificateById)) {
        updateCertificateFields(certificateById, giftCertificate);
        giftCertificateRepository.update(giftCertificate);
      }
      if (giftCertificate.getTags() != null && !giftCertificate.getTags().isEmpty()) {
        for (Tag tag : giftCertificate.getTags()) {
          if (!certificateById.getTags().remove(tag)) {
            tagService.saveCertificateTag(tag, giftCertificate.getId());
          }
        }
        for (Tag tag : certificateById.getTags()) {
          tagService.deleteTagForCertificate(tag.getId(), certificateById.getId());
        }
      }

    } catch (RepositoryUpdateException e) {
      throw new GiftCertificateServiceException("Service exception : Couldn't update certificate ");
    } catch (EntityNotFoundException e) {
      throw new GiftCertificateServiceException("Service exception : Couldn't find certificate ");
    } catch (TagServiceException e) {
      throw new GiftCertificateServiceException(
          "Service exception : Couldn't update tag certificate ");
    }
  }


  @Override
  public List<GiftCertificate> getCertificateByTagName(String tagName) {
    List<GiftCertificate> giftCertificates = giftCertificateRepository
        .getGiftCertificatesByTagName(tagName);
    giftCertificates.forEach(giftCertificate -> giftCertificate
        .setTags(new HashSet<>(tagService.getTagsByCertificateId(giftCertificate.getId()))));
    return giftCertificates;
  }

  private void updateCertificateFields(GiftCertificate certificate,
      GiftCertificate updateCertificate) {
    updateCertificate.setCreateDate(
        updateCertificate.getCreateDate() != null ? updateCertificate.getCreateDate()
            : certificate.getCreateDate());
    updateCertificate.setDescription(
        updateCertificate.getDescription() != null ? updateCertificate.getDescription()
            : certificate.getDescription());
    updateCertificate.setDuration(
        updateCertificate.getDuration() != null && updateCertificate.getDuration() > 0
            ? updateCertificate.getDuration()
            : certificate.getDuration());
    updateCertificate.setLastUpdateDate(ServiceUtils.getCurrentDateTime());
    updateCertificate
        .setPrice(updateCertificate.getPrice() != null && updateCertificate.getPrice().compareTo(
            BigDecimal.ZERO) > 0 ? updateCertificate.getPrice()
            : certificate.getPrice());
  }
}
