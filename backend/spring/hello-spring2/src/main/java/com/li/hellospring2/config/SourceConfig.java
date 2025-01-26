package com.li.hellospring2.config;

import com.li.hellospring2.util.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SourceConfig {

    @Profile({"dev", "default"})
    @Bean
    public DataSource dev() {
        return new DataSource(
                "jdbc:mysql://localhost:3306/dev",
                "dev",
                "dev"
        );
    }

    @Profile("test")
    @Bean
    public DataSource test() {
        return new DataSource(
                "jdbc:mysql://localhost:3306/test",
                "test",
                "test"
        );
    }

    @Profile("product")
    @Bean
    public DataSource product() {
        return new DataSource(
                "jdbc:mysql://localhost:3306/",
                "root",
                "root"
        );
    }
}
