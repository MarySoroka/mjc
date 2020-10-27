package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateServiceException;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface GiftCertificatesService {
    List<GiftCertificate> getAllCertificates();
    GiftCertificate getCertificateById(Long id) throws GiftCertificateServiceException;
    boolean createCertificate(GiftCertificate giftCertificate) throws GiftCertificateServiceException;
    boolean deleteCertificate(Long certificateId);
    boolean updateCertificate(GiftCertificate giftCertificate);
    List<GiftCertificate> getCertificateByTagName(Tag tag);
}
