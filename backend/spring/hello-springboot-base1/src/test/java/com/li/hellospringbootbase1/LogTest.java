package com.li.hellospringbootbase1;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class LogTest {

    //    Logger log = LoggerFactory.getLogger(LogTest.class);
    @Test
    void logTest() {


        log.trace("trace");
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");

        int a = 1;
        log.info("abc.a={}", a++);
        log.info("abc.a={}", a++);
        log.info("abc.a={}", a++);
    }
}
