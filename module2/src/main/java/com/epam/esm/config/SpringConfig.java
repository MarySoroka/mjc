package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:database.properties")
@ComponentScan("com.epam.esm")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {

    private final Environment environment;

    @Autowired
    public SpringConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public DataSource getDataSource() {
        HikariConfig databaseConfig = new HikariConfig();
        databaseConfig.setDriverClassName(environment.getProperty("database.className"));
        databaseConfig.setJdbcUrl(environment.getProperty("database.url"));
        databaseConfig.setUsername(environment.getProperty("database.user"));
        databaseConfig.setPassword(environment.getProperty("database.password"));
        databaseConfig.setMaximumPoolSize(100);
        return new HikariDataSource(databaseConfig);
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

}
