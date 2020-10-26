package com.epam.esm.gift_certificate;

import com.epam.esm.tag.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificatesServiceImpl implements GiftCertificatesService {
    @Override
    public List<GiftCertificate> getAllCertificates() {
        return null;
    }

    @Override
    public GiftCertificate getCertificateById(Long id) {
        return null;
    }

    @Override
    public boolean createCertificate(GiftCertificate giftCertificate) {
        return false;
    }

    @Override
    public boolean deleteCertificate(Long certificateId) {
        return false;
    }

    @Override
    public boolean updateCertificate(GiftCertificate giftCertificate) {
        return false;
    }

    @Override
    public boolean saveCertificate(GiftCertificate giftCertificate) {
        return false;
    }

    @Override
    public List<GiftCertificate> getCertificateByTagName(Tag tag) {
        return null;
    }
}
