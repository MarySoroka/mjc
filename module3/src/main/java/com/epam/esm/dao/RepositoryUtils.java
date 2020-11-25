package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificateFields;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RepositoryUtils {

  private RepositoryUtils() {
  }

  public static Map<String, String> getFilterStringByParams(Map<String, String> queryParams) {
    Map<String, String> resultFilterMap = new HashMap<>();
    Set<Entry<String, String>> entries = queryParams.entrySet();
    for (Entry<String, String> entry : entries) {
      String k = entry.getKey();
      if (GiftCertificateFields.of(k).isPresent()) {
        resultFilterMap.put(k, entry.getValue());
      }
    }
    return resultFilterMap;
  }

}
