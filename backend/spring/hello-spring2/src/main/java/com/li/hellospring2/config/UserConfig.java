package com.li.hellospring2.config;

import com.li.hellospring2.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class UserConfig {

    @Scope("singleton")
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public User user() {
        return new User();
    }
}
