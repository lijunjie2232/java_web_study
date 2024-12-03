package com.li.hellospring2.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
@PropertySource("classpath:dog.properties")
public class Dog {
    @Value("dog name")
    private String name;
    @Value("${dog.age:0}")
    private int age;
    @Value("#{'Hello World!'.substring(0,5)}")
    private String hello;
    @Value("#{T(java.util.UUID).randomUUID().toString()}")
    private String uuid;
}
