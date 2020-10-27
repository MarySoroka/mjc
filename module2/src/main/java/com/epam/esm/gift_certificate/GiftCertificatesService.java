package com.epam.esm.gift_certificate;

import com.epam.esm.tag.Tag;

import java.util.List;

public interface GiftCertificatesService {
    List<GiftCertificate> getAllCertificates();
    GiftCertificate getCertificateById(Long id) throws GiftCertificateServiceException;
    boolean createCertificate(GiftCertificate giftCertificate) throws GiftCertificateServiceException;
    boolean deleteCertificate(Long certificateId);
    boolean updateCertificate(GiftCertificate giftCertificate);
    List<GiftCertificate> getCertificateByTagName(Tag tag);
}
