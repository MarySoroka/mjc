package com.epam.esm.dao;


import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Map;

/**
 * interface for gift certificate repository
 */
public interface GiftCertificatesRepository extends CRUDDao<GiftCertificate, Long> {
    List<GiftCertificate> getGiftCertificatesByTagName(String tagName);
    List<GiftCertificate> getAllByQuery(Map<String, String> queryParam);
}
