package com.goldeneye.rings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.dialect.JdbcDb2Dialect;
import org.springframework.data.relational.core.dialect.Dialect;

@Configuration
public class DataSourceConfig {

    @Bean
    public Dialect jdbcDialect() {
        return JdbcDb2Dialect.INSTANCE;
    }
}