package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificatesRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DaoSaveException;
import com.epam.esm.exception.GiftCertificateServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GiftCertificatesServiceImpl implements GiftCertificatesService {
    private final GiftCertificatesRepository giftCertificatesRepository;
    private final TagsService tagsService;

    @Autowired
    public GiftCertificatesServiceImpl(GiftCertificatesRepository giftCertificatesRepository, TagsService tagsService) {
        this.giftCertificatesRepository = giftCertificatesRepository;
        this.tagsService = tagsService;
    }

    @Override
    public List<GiftCertificate> getAllCertificates(Map<String, String> queryParams) {
        List<GiftCertificate> giftCertificates;
        giftCertificates = giftCertificatesRepository.getAllByQuery(queryParams);
        giftCertificates.forEach(giftCertificate -> giftCertificate.setTags(new HashSet<>(tagsService.getTagsByCertificateId(giftCertificate.getId()))));
        return giftCertificates;
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
    @Transactional
    public boolean createCertificate(GiftCertificate giftCertificate) throws GiftCertificateServiceException {
        try {
            return giftCertificatesRepository.save(giftCertificate) > 0;
        } catch (DaoSaveException e) {
            throw new GiftCertificateServiceException("Couldn't save certificate");
        }
    }

    @Override
    @Transactional
    public boolean deleteCertificate(Long certificateId) {
        return giftCertificatesRepository.delete(certificateId);
    }

    @Override
    @Transactional
    public boolean updateCertificate(GiftCertificate giftCertificate) {
        return giftCertificatesRepository.update(giftCertificate);
    }


    @Override
    public List<GiftCertificate> getCertificateByTagName(String tagName) {
        List<GiftCertificate> giftCertificates = giftCertificatesRepository.getGiftCertificatesByTagName(tagName);
        giftCertificates.forEach(giftCertificate -> giftCertificate.setTags(new HashSet<>(tagsService.getTagsByCertificateId(giftCertificate.getId()))));
        return giftCertificates;
    }
}
