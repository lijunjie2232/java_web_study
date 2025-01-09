package com.li.hello_spring_practice1.config;

import com.li.hello_spring_practice1.interceptor.MyHandlerInterceptor;
import com.li.hello_spring_practice1.interceptor.MyHandlerInterceptor1;
import com.li.hello_spring_practice1.interceptor.MyHandlerInterceptor2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

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
    @Autowired
    MyHandlerInterceptor1 myHandlerInterceptor1;
    @Autowired
    MyHandlerInterceptor2 myHandlerInterceptor2;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myHandlerInterceptor).addPathPatterns("/**");
        registry.addInterceptor(myHandlerInterceptor1).addPathPatterns("/**");
        registry.addInterceptor(myHandlerInterceptor2).addPathPatterns("/**");
    }

}

