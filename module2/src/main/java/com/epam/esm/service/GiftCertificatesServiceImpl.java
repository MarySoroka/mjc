package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificatesRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.exception.GiftCertificateServiceException;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.exception.RepositoryUpdateException;
import com.epam.esm.exception.TagServiceException;
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
public class GiftCertificatesServiceImpl implements GiftCertificatesService {

  private final GiftCertificatesRepository giftCertificatesRepository;
  private final TagsService tagsService;

  @Autowired
  public GiftCertificatesServiceImpl(GiftCertificatesRepository giftCertificatesRepository,
      TagsService tagsService) {
    this.giftCertificatesRepository = giftCertificatesRepository;
    this.tagsService = tagsService;
  }

  @Override
  public List<GiftCertificate> getAllCertificates(Map<String, String> queryParams) {
    List<GiftCertificate> giftCertificates;
    giftCertificates = giftCertificatesRepository.getAllByQuery(queryParams);
    giftCertificates.forEach(giftCertificate -> giftCertificate
        .setTags(new HashSet<>(tagsService.getTagsByCertificateId(giftCertificate.getId()))));
    return giftCertificates;
  }

  @Override
  public GiftCertificate getCertificateById(Long id)
      throws GiftCertificateNotFoundException {
    Optional<GiftCertificate> giftCertificate = giftCertificatesRepository.getById(id);
    if (!giftCertificate.isPresent()) {
      throw new GiftCertificateNotFoundException(
          "Service exception : Couldn't get certificate by id : " + id);
    }
    giftCertificate.get()
        .setTags(new HashSet<>(tagsService.getTagsByCertificateId(giftCertificate.get().getId())));
    return giftCertificate.get();
  }

  @Override
  @Transactional
  public Long createCertificate(GiftCertificate giftCertificate)
      throws GiftCertificateServiceException {
    try {
      LocalDateTime currentDateTime = ServiceUtils.getCurrentDateTime();
      giftCertificate.setLastUpdateDate(currentDateTime);
      giftCertificate.setCreateDate(currentDateTime);
      Long certificateId = giftCertificatesRepository.save(giftCertificate);
      Set<Tag> tags = giftCertificate.getTags();
      if (tags!=null && !tags.isEmpty()) {
        for (Tag tag : tags) {
          tagsService.saveCertificateTag(tag, certificateId);
        }
      }
      return certificateId;
    } catch (RepositorySaveException e) {
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
      giftCertificatesRepository.delete(certificateId);
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
        giftCertificatesRepository.update(giftCertificate);
      }
      if (giftCertificate.getTags()!=null && !giftCertificate.getTags().isEmpty()) {
        for (Tag tag : giftCertificate.getTags()) {
          if(!certificateById.getTags().remove(tag)){
            tagsService.saveCertificateTag(tag, giftCertificate.getId());
          }
        }
        for (Tag tag : certificateById.getTags()) {
          tagsService.deleteTagForCertificate(tag.getId(), certificateById.getId());
        }
      }

    } catch (RepositoryUpdateException e) {
      throw new GiftCertificateServiceException("Service exception : Couldn't update certificate ");
    } catch (GiftCertificateNotFoundException e) {
      throw new GiftCertificateServiceException("Service exception : Couldn't find certificate ");
    } catch (TagServiceException e) {
      throw new GiftCertificateServiceException(
          "Service exception : Couldn't update tag certificate ");
    }
  }


  @Override
  public List<GiftCertificate> getCertificateByTagName(String tagName) {
    List<GiftCertificate> giftCertificates = giftCertificatesRepository
        .getGiftCertificatesByTagName(tagName);
    giftCertificates.forEach(giftCertificate -> giftCertificate
        .setTags(new HashSet<>(tagsService.getTagsByCertificateId(giftCertificate.getId()))));
    return giftCertificates;
  }

  private void updateCertificateFields(GiftCertificate certificate,
      GiftCertificate updateCertificate) {
    updateCertificate
        .setId(updateCertificate.getId() != null ? updateCertificate.getId() : certificate.getId());
    updateCertificate.setCreateDate(
        updateCertificate.getCreateDate() != null ? updateCertificate.getCreateDate()
            : certificate.getCreateDate());
    updateCertificate.setDescription(
        updateCertificate.getDescription() != null ? updateCertificate.getDescription()
            : certificate.getDescription());
    updateCertificate.setDuration(
        updateCertificate.getDuration() != null ? updateCertificate.getDuration()
            : certificate.getDuration());
    updateCertificate.setLastUpdateDate(ServiceUtils.getCurrentDateTime());
    updateCertificate.setPrice(updateCertificate.getPrice() != null ? updateCertificate.getPrice()
        : certificate.getPrice());
  }
}
