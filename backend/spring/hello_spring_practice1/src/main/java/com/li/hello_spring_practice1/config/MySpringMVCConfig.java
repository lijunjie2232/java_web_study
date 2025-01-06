package com.li.hello_spring_practice1.config;

import com.li.hello_spring_practice1.interceptor.MyHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/// / method 1
//@Configuration
//public class MySpringMVCConfig {
//
//    @Autowired
//    MyHandlerInterceptor myHandlerInterceptor;
//    @Bean
//    WebMvcConfigurer webMvcConfigurer(){
//        return new WebMvcConfigurer() {
//            @Override
//            public void addInterceptors(InterceptorRegistry registry) {
//                registry.addInterceptor(myHandlerInterceptor).addPathPatterns("/**");
//            }
//        };
//    }
//}

// method 2
@Configuration
public class MySpringMVCConfig implements WebMvcConfigurer {

    @Autowired
    MyHandlerInterceptor myHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myHandlerInterceptor).addPathPatterns("/**");
    }
}

