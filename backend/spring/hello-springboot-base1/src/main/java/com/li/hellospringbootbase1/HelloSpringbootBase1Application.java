package com.li.hellospringbootbase1;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

@SpringBootApplication
public class HelloSpringbootBase1Application {

    public static void main(String[] args) {

//        method 1
        SpringApplication.run(HelloSpringbootBase1Application.class, args);
//        method 2
//        SpringApplication springApplication = new SpringApplication(HelloSpringbootBase1Application.class);
//        springApplication.setLazyInitialization(true);
//        springApplication.setBannerMode(Banner.Mode.OFF);
//        springApplication.setEnvironment(null);
//        springApplication.run(args);
//        method 3
//        SpringApplicationBuilder builder = new SpringApplicationBuilder();
//        builder
//                .sources(HelloSpringbootBase1Application.class)
//                .bannerMode(Banner.Mode.OFF)
//                .environment(null)
//                .lazyInitialization(true)
//                .run(args);

    }

}
