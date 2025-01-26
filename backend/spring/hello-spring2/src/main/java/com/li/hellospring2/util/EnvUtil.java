package com.li.hellospring2.util;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@ToString
@Component
public class EnvUtil implements EnvironmentAware, BeanNameAware, BeanFactoryAware {
    private Environment env;
    private BeanFactory beanFactory;
    private String beanName;

    public void setEnvironment(Environment env) {
        this.env = env;
    }
    public Environment getEnvironment() {
        return env;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
