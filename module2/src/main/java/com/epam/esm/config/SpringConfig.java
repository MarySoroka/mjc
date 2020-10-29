package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:database.properties")
@ComponentScan("com.epam.esm")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {

    private final String className;
    private final String url;
    private final String user;
    private final String password;
    private final Integer poolMaxSize;

    @Autowired
    public SpringConfig(@Value("${database.className}") String className,
                        @Value("${database.url}") String url,
                        @Value("${database.user}") String user,
                        @Value("${database.password}") String password,
                        @Value("${database.maxConnectionPoolSize}") Integer poolMaxSize) {
        this.className = className;
        this.url = url;
        this.user = user;
        this.password = password;
        this.poolMaxSize = poolMaxSize;
    }

    @Bean
    public DataSource getDataSource() {
        HikariConfig databaseConfig = new HikariConfig();
        databaseConfig.setDriverClassName(this.className);
        databaseConfig.setJdbcUrl(this.url);
        databaseConfig.setUsername(this.user);
        databaseConfig.setPassword(this.password);
        databaseConfig.setMaximumPoolSize(this.poolMaxSize);
        return new HikariDataSource(databaseConfig);
    }

    @Bean
    public NamedParameterJdbcTemplate getJdbcTemplate() {
        return new NamedParameterJdbcTemplate(getDataSource());
    }

}
