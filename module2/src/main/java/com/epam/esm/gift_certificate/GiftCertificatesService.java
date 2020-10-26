package com.epam.esm.gift_certificate;

import com.epam.esm.tag.Tag;

import java.util.List;

public interface GiftCertificatesService {
    List<GiftCertificate> getAllCertificates();
    GiftCertificate getCertificateById(Long id);
    boolean createCertificate(GiftCertificate giftCertificate);
    boolean deleteCertificate(Long certificateId);
    boolean updateCertificate(GiftCertificate giftCertificate);
    boolean saveCertificate(GiftCertificate giftCertificate);
    List<GiftCertificate> getCertificateByTagName(Tag tag);
}
