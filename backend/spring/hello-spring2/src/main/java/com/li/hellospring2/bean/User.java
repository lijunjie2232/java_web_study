package com.li.hellospring2.bean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

@ToString
@Data
@AllArgsConstructor
public class User implements InitializingBean, DisposableBean {
    private String name;

    public User() {
        System.out.println("user constructor");
    }

    public void init() {
        System.out.println("user init");
    }

    public void destroy() {
        System.out.println("user destroy");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("user post construct");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("user pre destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("user InitializingBean");
    }

}
