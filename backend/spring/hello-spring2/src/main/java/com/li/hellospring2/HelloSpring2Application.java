package com.li.hellospring2;

import com.li.hellospring2.bean.Person;
import com.li.hellospring2.factory.PFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Map;

//@ComponentScan(basePackages = "com.li")
@SpringBootApplication
public class HelloSpring2Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext ioc = SpringApplication.run(HelloSpring2Application.class, args);
        System.out.println(ioc);

        String[] beanNames = ioc.getBeanDefinitionNames();
        for (String bean : beanNames) {
            System.out.println(bean);
        }

//        ioc.getBean("li");// org.springframework.beans.factory.NoSuchBeanDefinitionException
//        System.out.println(ioc.getBean(Person.class).getName());//    org.springframework.beans.factory.NoUniqueBeanDefinitionException
        System.out.println(((Person) ioc.getBean("personli")).getName());
        Map<String, Person> type = ioc.getBeansOfType(Person.class);
        System.out.println(type.toString());
        System.out.println(ioc.getBean("personli", Person.class).getName());
        Map<String, PFactory> type1 = ioc.getBeansOfType(PFactory.class);
        System.out.println(type1.toString());// not Person, just PFactory component instance
        System.out.println(((Person) ioc.getBean("PFactory")).getName());


    }
}
