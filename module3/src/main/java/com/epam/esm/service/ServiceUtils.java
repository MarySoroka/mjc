package com.epam.esm.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

public class ServiceUtils {

  private ServiceUtils() {
  }

  /**
   * @return current date in iso 8601 format
   */
  public static LocalDateTime getCurrentDateTime() {
    TimeZone tz = TimeZone.getTimeZone("UTC");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
    df.setTimeZone(tz);
    return LocalDateTime.from(ZonedDateTime.parse(df.format(new Date())));
  }
}
