package com.epam.esm.dao;

import static org.junit.Assert.assertTrue;
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
    Map<String, String> filterStringByParams = RepositoryUtils.getFilterStringByParams(queryParams);
    assertTrue(filterStringByParams.containsKey("name"));
    assertEquals("lala", filterStringByParams.get("name"));
  }

}