package com.epam.esm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class ConfigUtils {
    @Autowired
    public ConfigUtils() {

    }

    public Properties getJdbcConfig(String propertyFile) throws IOException {
        InputStream propertyFileStream = this.getClass().getResourceAsStream(propertyFile);
        Properties jdbcProperties = new Properties();
        jdbcProperties.load(propertyFileStream);
        return jdbcProperties;
    }
}
