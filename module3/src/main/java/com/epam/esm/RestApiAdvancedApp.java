package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.epam.esm.entity")
public class RestApiAdvancedApp {

  public static void main(String[] args) {
    SpringApplication.run(RestApiAdvancedApp.class, args);
  }
}
