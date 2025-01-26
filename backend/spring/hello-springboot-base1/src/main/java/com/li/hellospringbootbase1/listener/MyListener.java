package com.li.hellospringbootbase1.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.time.Duration;

@Slf4j
public class MyListener implements SpringApplicationRunListener {
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        log.info("[MyListener] starting");
    }

    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        log.info("[MyListener] environmentPrepared");
    }

    public void contextPrepared(ConfigurableApplicationContext context) {
        log.info("[MyListener] contextPrepared");
    }

    public void contextLoaded(ConfigurableApplicationContext context) {
        log.info("[MyListener] contextLoaded");
    }

    public void started(ConfigurableApplicationContext context, Duration timeTaken) {
        log.info("[MyListener] started");
    }

    public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
        log.info("[MyListener] ready");
    }

    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log.info("[MyListener] failed");
    }

}
