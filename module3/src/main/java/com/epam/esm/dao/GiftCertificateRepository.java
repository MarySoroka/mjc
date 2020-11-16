package com.epam.esm.dao;


import com.epam.esm.entity.GiftCertificate;
import java.util.List;
import java.util.Map;

/**
 * interface for gift certificate repository
 */
public interface GiftCertificateRepository extends CRUDDao<GiftCertificate, Long> {

  /**
   * method gift certificates by tag name
   *
   * @param tagName tag name
   * @return gift certificates which have this tag
   */
  List<GiftCertificate> getGiftCertificatesByTagName(String tagName, Map<String, Integer> pagination);

  /**
   * method return all certificates by query parameters
   *
   * @param queryParam query parameters
   * @return gift certificates
   */
  List<GiftCertificate> getAllByQuery(Map<String, String> queryParam, Map<String, Integer> pagination);
}
