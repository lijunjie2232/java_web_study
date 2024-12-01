package com.li.hellospring2.config;

import com.li.hellospring2.bean.Person;
import com.li.hellospring2.condition.PersonConditin;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.*;

@Configuration
public class PersonConfig {

    //    @Scope()
    @Primary
    @Lazy
    @Bean("personli")
    public Person person1() {
        Person person = new Person();
        person.setAge(0);
        person.setName("li");
        return person;
    }


    //    @Conditional(PersonConditin.class)
    @ConditionalOnMissingBean(name = "personli2", value = {Person.class})
    @ConditionalOnResource(resources = "classpath:db.properties")
    @Bean("personli2")
    public Person person2() {
        Person person = new Person();
        person.setAge(1);
        person.setName("li");
        return person;
    }
}
