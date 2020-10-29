package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateServiceException;

import java.util.List;
import java.util.Map;

/**
 * interface that demonstrate business logic for gift certificate domain
 */

public interface GiftCertificatesService {

    List<GiftCertificate> getAllCertificates(Map<String, String> queryParams);

    /**
     * method find entity by id parameter
     *
     * @param id certificate id, which is using to find entity
     * @return entity or Optional.empty(), if entity is not found by this key
     */
    GiftCertificate getCertificateById(Long id) throws GiftCertificateServiceException;

    /**
     * method save entity
     *
     * @param giftCertificate certificate entity, that should be save
     * @return if entity has been saved return generated id
     * @throws GiftCertificateServiceException if generated id equals null
     */
    boolean createCertificate(GiftCertificate giftCertificate) throws GiftCertificateServiceException;

    /**
     * delete entity by key parameter
     *
     * @param certificateId certificate id
     * @return if entity has been found and deleted - true, else false
     */
    boolean deleteCertificate(Long certificateId);

    /**
     * method update entity using new values in giftCertificate
     *
     * @param giftCertificate new values for entity
     * @return if entity has been found and updated - true, else false
     */
    boolean updateCertificate(GiftCertificate giftCertificate);

    List<GiftCertificate> getCertificateByTagName(String tagName);
}
