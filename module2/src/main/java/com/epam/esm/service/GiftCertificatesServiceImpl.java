package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificatesRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DaoSaveException;
import com.epam.esm.exception.GiftCertificateServiceException;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificatesServiceImpl implements GiftCertificatesService {
    private final GiftCertificatesRepository giftCertificatesRepository;

    @Autowired
    public GiftCertificatesServiceImpl(GiftCertificatesRepository giftCertificatesRepository) {
        this.giftCertificatesRepository = giftCertificatesRepository;
    }

    @Override
    public List<GiftCertificate> getAllCertificates() {
        return giftCertificatesRepository.getAll();
    }

    @Override
    public GiftCertificate getCertificateById(Long id) throws GiftCertificateServiceException {
        Optional<GiftCertificate> giftCertificate = giftCertificatesRepository.getById(id);
        if (!giftCertificate.isPresent()) {
            throw new GiftCertificateServiceException();
        }
        return giftCertificate.get();
    }

    @Override
    public boolean createCertificate(GiftCertificate giftCertificate) throws GiftCertificateServiceException {
        try {
            return giftCertificatesRepository.save(giftCertificate) > 0;
        } catch (DaoSaveException e) {
            throw new GiftCertificateServiceException("Couldn't save certificate");
        }
    }

    @Override
    public boolean deleteCertificate(Long certificateId) {
        return giftCertificatesRepository.delete(certificateId);
    }

    @Override
    public boolean updateCertificate(GiftCertificate giftCertificate) {
        return giftCertificatesRepository.update(giftCertificate);
    }



    @Override
    public List<GiftCertificate> getCertificateByTagName(Tag tag) {
        return null;
    }
}
