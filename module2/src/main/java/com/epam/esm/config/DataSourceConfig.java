package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@PropertySource("classpath:database.properties")
@ComponentScan("com.epam.esm")
@EnableWebMvc
public class DataSourceConfig {

  @Value("${database.url}")
  private String url;
  @Value("${database.user}")
  private String user;
  @Value("${database.password}")
  private String password;
  @Value("${database.className}")
  private String className;
  @Value("${database.maxConnectionPoolSize}")
  private Integer poolMaxSize;

  @Bean
  public DataSource getDataSource() {
    HikariConfig databaseConfig = new HikariConfig();
    databaseConfig.setDriverClassName(className);
    databaseConfig.setJdbcUrl(url);
    databaseConfig.setUsername(user);
    databaseConfig.setPassword(password);
    databaseConfig.setMaximumPoolSize(poolMaxSize);
    return new HikariDataSource(databaseConfig);
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    return new DataSourceTransactionManager(getDataSource());
  }

  @Bean
  public NamedParameterJdbcTemplate getJdbcTemplate() {
    return new NamedParameterJdbcTemplate(getDataSource());
  }

}
