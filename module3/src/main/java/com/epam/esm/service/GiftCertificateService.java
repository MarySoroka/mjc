package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.GiftCertificateServiceException;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.exception.TagServiceException;
import java.util.List;
import java.util.Map;

/**
 * interface that demonstrate business logic for gift certificate domain
 */

public interface GiftCertificateService {

  List<GiftCertificate> getAllCertificates(Map<String, String> queryParams);

  /**
   * method find entity by id parameter
   *
   * @param id certificate id, which is using to find entity
   * @return entity or Optional.empty(), if entity is not found by this key
   */
  GiftCertificate getCertificateById(Long id)
      throws EntityNotFoundException;

  /**
   * method save entity
   *
   * @param giftCertificate certificate entity, that should be save
   * @return if entity has been saved return generated id
   */
  GiftCertificate createCertificate(GiftCertificate giftCertificate)
      throws TagServiceException, EntityNotFoundException, RepositorySaveException;

  /**
   * delete entity by key parameter
   *
   * @param certificateId certificate id
   */
  void deleteCertificate(Long certificateId)
      throws  RepositoryDeleteException;

  /**
   * method update entity using new values in giftCertificate
   *
   * @param giftCertificate new values for entity
   */
  void updateCertificate(GiftCertificate giftCertificate)
      throws GiftCertificateServiceException;

  /**
   * method return certificate by tag name
   *
   * @param tagName tag name
   * @return certificates
   */
  List<GiftCertificate> getCertificateByTagName(String tagName, Map<String, Integer> pagination);
}
