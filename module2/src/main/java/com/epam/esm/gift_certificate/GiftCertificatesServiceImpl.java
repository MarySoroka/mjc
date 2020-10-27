package com.epam.esm.gift_certificate;

import com.epam.esm.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificatesServiceImpl implements GiftCertificatesService {
    private final GiftCertificatesDao giftCertificatesDao;

    @Autowired
    public GiftCertificatesServiceImpl(GiftCertificatesDao giftCertificatesDao) {
        this.giftCertificatesDao = giftCertificatesDao;
    }

    @Override
    public List<GiftCertificate> getAllCertificates() {
        return giftCertificatesDao.getAll();
    }

    @Override
    public GiftCertificate getCertificateById(Long id) throws GiftCertificateServiceException {
        Optional<GiftCertificate> giftCertificate = giftCertificatesDao.getById(id);
        if (!giftCertificate.isPresent()) {
            throw new GiftCertificateServiceException();
        }
        return giftCertificate.get();
    }

    @Override
    public boolean createCertificate(GiftCertificate giftCertificate) throws GiftCertificateServiceException {
        try {
            return giftCertificatesDao.save(giftCertificate) > 0;
        } catch (GiftCertificateSaveException e) {
            throw new GiftCertificateServiceException("Couldn't save certificate");
        }
    }

    @Override
    public boolean deleteCertificate(Long certificateId) {
        return giftCertificatesDao.delete(certificateId);
    }

    @Override
    public boolean updateCertificate(GiftCertificate giftCertificate) {
        return giftCertificatesDao.update(giftCertificate);
    }



    @Override
    public List<GiftCertificate> getCertificateByTagName(Tag tag) {
        return null;
    }
}
