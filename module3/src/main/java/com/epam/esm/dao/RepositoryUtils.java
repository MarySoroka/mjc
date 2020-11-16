package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificateFields;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RepositoryUtils {

  private static final String HAVING = " HAVING ";
  private static final String CERTIFICATE_TABLE_NAME = "gc.";
  private static final String LIKE_START = " LIKE '%";
  private static final String LIKE_END = "%' ";
  private static final String AND = " AND ";
  private static final String ORDER_BY = " ORDER BY gc.";
  private static final String SORT = "sort";
  private static final String ORDER = "order";
  private static final String LIMIT = "limit";
  private static final String OFFSET = "offset";


  private RepositoryUtils() {
  }

  public static String getFilterStringByParams(Map<String, String> queryParams) {
    StringBuilder sql = new StringBuilder(HAVING);
    Map<String, String> resultFilterMap = new HashMap<>();
    Set<Entry<String, String>> entries = queryParams.entrySet();
    for (Entry<String, String> entry : entries) {
      String k = entry.getKey();
      if (GiftCertificateFields.of(k).isPresent()) {
        resultFilterMap.put(k, entry.getValue());
      }
    }
    resultFilterMap.forEach(
        (key, value) ->
            sql
                .append(CERTIFICATE_TABLE_NAME)
                .append(key)
                .append(LIKE_START)
                .append(value)
                .append(LIKE_END)
                .append(AND));
    return sql.substring(0, sql.length() - 5);
  }

  public static String getSortStringByQuery(Map<String, String> queryParam) {
    StringBuilder sql = new StringBuilder();
    if (queryParam.containsKey(SORT) && GiftCertificateFields.of(queryParam.get(SORT))
        .isPresent()) {
      sql.append(ORDER_BY).append(queryParam.get(SORT));
      if (queryParam.containsKey(ORDER)) {
        sql.append(" ").append(queryParam.get(ORDER));
      }
    }
    return sql.toString();
  }

  public static String getLimitStringByQuery(Map<String, String> queryParam) {
    StringBuilder sql = new StringBuilder();
    if (queryParam.containsKey(LIMIT)) {
      String limit = queryParam.get(LIMIT);
      String offset = queryParam.get(OFFSET);
      sql.append(" LIMIT ").append(Integer.parseInt(limit));
      sql.append(" OFFSET ").append(Integer.parseInt(offset));
    }
    return sql.toString();
  }
}
