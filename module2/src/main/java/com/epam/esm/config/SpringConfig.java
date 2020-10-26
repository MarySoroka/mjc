package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;
    private final ConfigUtils configUtils;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext, ConfigUtils configUtils) {
        this.applicationContext = applicationContext;
        this.configUtils = configUtils;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }

    @Bean
    public DataSource getDataSource() throws IOException {
        Properties jdbcConfig = configUtils.getJdbcConfig("database.properties");
        HikariConfig databaseConfig = new HikariConfig();
        databaseConfig.setDataSourceClassName(jdbcConfig.getProperty("database.className"));
        databaseConfig.setJdbcUrl(jdbcConfig.getProperty("database.url"));
        databaseConfig.setUsername(jdbcConfig.getProperty("database.user"));
        databaseConfig.setPassword(jdbcConfig.getProperty("database.password"));
        databaseConfig.addDataSourceProperty("cachePrepStmts", "true");
        databaseConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        databaseConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        databaseConfig.setMaximumPoolSize(100);
        return new HikariDataSource(databaseConfig);
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() throws IOException {
        return new JdbcTemplate(getDataSource());
    }

}
