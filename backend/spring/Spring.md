<!-- TOC -->
* [Spring Container](#spring-container)
  * [ConfigurableApplicationContext](#configurableapplicationcontext)
    * [@Bean](#bean)
    * [getBean](#getbean)
  * [@Configuration](#configuration)
  * [Spring MVC Annotation](#spring-mvc-annotation)
  * [@Import](#import)
  * [@Scope](#scope)
  * [@Lazy](#lazy)
  * [FactoryBean](#factorybean)
  * [Condition](#condition)
* [Inject](#inject)
  * [@Autowired](#autowired)
  * [@Qualifier("personli")](#qualifierpersonli)
  * [@Primary](#primary)
  * [@Resource](#resource)
  * [component constructor inject](#component-constructor-inject)
  * [setter inject](#setter-inject)
<!-- TOC -->


# Spring Container
## ConfigurableApplicationContext
- bean constructed on container construction
- all beans is singleton instantiation (exclude prototype scope bean)
```java
package com.li.hellospring2;

import com.li.hellospring2.bean.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

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

    }

    @Bean("personli")
    public Person person1() {
        Person person = new Person();
        person.setAge(0);
        person.setName("li");
        return person;
    }


    @Bean("personli2")
    public Person person2() {
        Person person = new Person();
        person.setAge(0);
        person.setName("li");
        return person;
    }
}
```
### @Bean
- regist bean into ioc container

### getBean
  1. use class of bean: `ioc.getBean(Person.class)` / `ioc.getBeansOfType(Person.class)`
      - throw `NoSuchBeanDefinitionException` if not found
      - throw `NoUniqueBeanDefinitionException` if more than one beans in class but get only one
  2. use name of bean: `(Person) ioc.getBean("personli")`
     - throw `NoSuchBeanDefinitionException` if not found
  3. use name and class at the same time: `System.out.println(ioc.getBean("personli", Person.class).getName());`

## @Configuration
- configuration class is also a bean in ioc container

```java
import com.li.hellospring2.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(Person.class)
@ComponentScan(basePackages = "com.li")
@Configuration
public class PersonConfig {

    @Bean("personli")
    public Person person1() {
        Person person = new Person();
        person.setAge(0);
        person.setName("li");
        return person;
    }


    @Bean("personli2")
    public Person person2() {
        Person person = new Person();
        person.setAge(0);
        person.setName("li");
        return person;
    }
}
```

## Spring MVC Annotation
- `@Controller` for controller
- `@Repository` for dao
- `@Service` for service
- `@Controller` / `@Repository` / `@Service` all could replaced with `@Component`
- !!! **all these annotation should be the same path with ioc container's path, or at the child path**
- use `@ComponentScan(basePackages = "com.li")` to scan components not in ioc path or child path

## @Import
- `@Import(Person.class)` to regist an components into ioc container

## @Scope
- `prototype`
- `singleton`
- `request`
- `session`

## @Lazy
- only work on singleton
- construct while get instance

## FactoryBean
```java
import com.li.hellospring2.bean.Person;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class PFactory implements FactoryBean<Person> {
    @Override
    public Person getObject() throws Exception {
        return new Person("li", 0);
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
```
- **name: "PFactory"**
- get bean: `ioc.getBeansOfType(Person.class)`

## Condition
```java
import com.li.hellospring2.bean.Person;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class PersonConditin implements Condition {
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getBeanFactory().getBeanNamesForType(Person.class).length <= 2;
    }
}
```
```java
@Conditional(PersonConditin.class)
@ConditionalOnMissingBean(name="personli2", value={Person.class})
@ConditionalOnResource(resources="classpath:db.properties")
@Bean("personli2")
public Person person2() {
    Person person = new Person();
    person.setAge(1);
    person.setName("li");
    return person;
}
```

# Inject
## @Autowired
1. first find component or bean by class
2. second find specified by name
3. `@Autowired(required = false)` allow wire as `null`
```java
package com.li.hellospring2.controller;

import com.li.hellospring2.bean.Person;
import com.li.hellospring2.service.UserService;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@ToString
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    Person PFactory;

    @Autowired
    Person personli;
}
```
## @Qualifier("personli")
- specify a bean by name

## @Primary
- **declare** a bean is primary bean for Autowired

## @Resource
- same function as @Autowired, but is a general interface of java
- `@Autowired(required = false)` allow do not has a bean bue `@Resource` will throw error 

## component constructor inject
- a component `@Component` only has constructor needs param, then auto find params while constructing
```java
public UserController(UserService userService) {
    System.out.println("UserController constructor");
    System.out.println(userService);
}
```

## setter inject
```java
Person person2;

@Autowired
@Qualifier("personli")
public void SetPerson2(Person personli){
    System.out.println("SetPerson2");
    System.out.println(personli);
    this.person2 = personli;
}
```