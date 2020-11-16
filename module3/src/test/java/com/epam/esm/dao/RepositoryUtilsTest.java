package com.epam.esm.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RepositoryUtilsTest {

  private static final Map<String, String> queryParams = new HashMap<>(3);

  @BeforeAll
  public static void setup() {
    queryParams.put("limit", "10");
    queryParams.put("offset", "0");
    queryParams.put("name", "lala");
    queryParams.put("sort", "create_date");
    queryParams.put("order", "asc");

  }

  @Test
  void getFilterStringByParams() {
    String filterStringByParams = RepositoryUtils.getFilterStringByParams(queryParams);
    assertEquals(" HAVING gc.name LIKE '%lala%' ",filterStringByParams);
  }

  @Test
  void getSortStringByQuery() {
    String sortStringByQuery = RepositoryUtils.getSortStringByQuery(queryParams);
    assertEquals(" ORDER BY gc.create_date asc",sortStringByQuery);
  }

  @Test
  void getLimitStringByQuery() {
    String limitStringByQuery = RepositoryUtils.getLimitStringByQuery(queryParams);
    assertEquals(" LIMIT 10 OFFSET 0",limitStringByQuery);
  }

}