package com.goldeneye.rings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication(scanBasePackages = "com.goldeneye")
@EnableJdbcRepositories(basePackages = "com.goldeneye.repo")
public class RingsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RingsApplication.class, args);
    }

    // @Bean
    // CommandLineRunner testConnection(DataSource dataSource) {
    //     return args -> {
    //         try (var connection = dataSource.getConnection()) {
    //             System.out.println("DB2 Connection successful: " + connection.getMetaData().getDatabaseProductVersion());
    //         }
    //     };
    // }
}