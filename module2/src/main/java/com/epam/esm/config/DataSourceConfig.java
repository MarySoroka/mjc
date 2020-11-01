package com.epam.esm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:database.properties")
@ComponentScan("com.epam.esm")
@EnableWebMvc
public class DataSourceConfig implements WebMvcConfigurer {

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

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    for (HttpMessageConverter<?> converter : converters) {
      if (converter instanceof MappingJackson2HttpMessageConverter) {
        MappingJackson2HttpMessageConverter jsonMessageConverter = (MappingJackson2HttpMessageConverter) converter;
        ObjectMapper objectMapper = jsonMessageConverter.getObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        break;
      }
    }
  }
}
