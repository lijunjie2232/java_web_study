package com.li.hellospring2;

import com.li.hellospring2.bean.Dog;
import com.li.hellospring2.bean.Person;
import com.li.hellospring2.controller.UserController;
import com.li.hellospring2.dao.UserDao;
import com.li.hellospring2.factory.PFactory;
import com.li.hellospring2.util.EnvUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ResourceUtils;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;

import java.io.*;
import java.util.Map;
import java.util.Properties;

//@ComponentScan(basePackages = "com.li")
@SpringBootApplication
public class HelloSpring2Application {

    public static void main(String[] args) throws IOException {

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
        System.out.println(type);
        System.out.println(ioc.getBean("personli", Person.class).getName());
        Map<String, PFactory> type1 = ioc.getBeansOfType(PFactory.class);
        System.out.println(type1);// not Person, just PFactory component instance
        System.out.println(((Person) ioc.getBean("PFactory")).getName());


        System.out.println(ioc.getBean(UserController.class));

        System.out.println(ioc.getBean(EnvUtil.class));
        System.out.println(ioc.getBean(Dog.class));

        File file = ResourceUtils.getFile("classpath:application.properties");
        System.out.println(file.length());

        Properties properties = new Properties();
        properties.load(new InputStreamReader(new FileInputStream(file)));
        System.out.println(properties);

        System.out.println(ioc.getBean(UserDao.class));
    }
}
