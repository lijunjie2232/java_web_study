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
  * [Aware](#aware)
  * [@Value](#value)
  * [@PropertySource](#propertysource)
  * [ResourceUtils](#resourceutils)
  * [Profile](#profile)
* [Spring LifeCycle](#spring-lifecycle)
  * [InitializingBean interface](#initializingbean-interface)
  * [DisposableBean interface](#disposablebean-interface)
  * [BeanPostProcessor](#beanpostprocessor)
  * [lefe cycle of bean](#lefe-cycle-of-bean)
* [SpringBootTest](#springboottest)
* [AOP](#aop)
  * [java dynamic proxy](#java-dynamic-proxy)
  * [Spring AOP](#spring-aop)
    * [@Aspect](#aspect)
    * [@PointCut](#pointcut)
    * [@Order(n)](#ordern)
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
@ConditionalOnMissingBean(name = "personli2", value = {Person.class})
@ConditionalOnResource(resources = "classpath:db.properties")
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
public void SetPerson2(Person personli) {
    System.out.println("SetPerson2");
    System.out.println(personli);
    this.person2 = personli;
}
```

## Aware

| **名称**                         | **用途**                                  | **所属容器**           | **回调点**                           |
|--------------------------------|-----------------------------------------|--------------------|-----------------------------------|
| BeanNameAware                  | 获取bean名称                                | BeanFactory        | Bean后处理器的BeforeInitialization方法之前 |
| BeanClassLoaderAware           | 获取bean的类加载器                             | BeanFactory        | Bean后处理器的BeforeInitialization方法之前 |
| BeanFactoryAware               | 获取bean工厂（建议用下面的ApplicationContextAware） | BeanFactory        | Bean后处理器的BeforeInitialization方法之前 |
| EnvironmentAware               | 获取环境相关信息，如属性、配置信息等                      | ApplicationContext | Bean后处理器的BeforeInitialization方法中  |
| EmbeddedValueResolverAware     | 获取值解析器                                  | ApplicationContext | Bean后处理器的BeforeInitialization方法中  |
| ResourceLoaderAware            | 获取资源加载器                                 | ApplicationContext | Bean后处理器的BeforeInitialization方法中  |
| ApplicationEventPublisherAware | 获取事件广播器，发布事件使用                          | ApplicationContext | Bean后处理器的BeforeInitialization方法中  |
| MessageSourceAware             | 获取消息资源                                  | ApplicationContext | Bean后处理器的BeforeInitialization方法中  |
| ApplicationContextAware        | 获取ApplicationContext                    | ApplicationContext | Bean后处理器的BeforeInitialization方法中  |

```java
package com.li.hellospring2.util;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@ToString
@Component
public class EnvUtil implements EnvironmentAware, BeanNameAware, BeanFactoryAware {
    private Environment environment;
    private BeanFactory beanFactory;
    private String beanName;

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
```

## @Value

- `@Value("value")` directly pass value (all type in string)
- `@Value("${properties_key:default_value}")` get value by key from `xxx.properties`, if not found, use `default_value`
- `@Value("#{SpEL}")` write java script

```java
package com.li.hellospring2.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
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

// Dog(name=dog name, age=1, hello=Hello, uuid=181cb078-10e1-4ba0-94ef-015c66cc1506)
```

## @PropertySource

- specified properties file: `@PropertySource("classpath:dog.properties")`
- `classpath*`: classpath of all projects

## ResourceUtils

```java
File file = ResourceUtils.getFile("classpath:application.properties");
System.out.

println(file.length());

Properties properties = new Properties();
properties.

load(new InputStreamReader(new FileInputStream(file)));
        System.out.

println(properties);
```

## Profile

- specify activate env in properties `spring.profiles.active=dev`
- decorate beans or components with `@Profile({"dev", "default"})`

```java
package com.li.hellospring2.config;

import com.li.hellospring2.util.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SourceConfig {

    @Profile({"dev", "default"})
    @Bean
    public DataSource dev() {
        return new DataSource(
                "jdbc:mysql://localhost:3306/dev",
                "dev",
                "dev"
        );
    }

    @Profile("test")
    @Bean
    public DataSource test() {
        return new DataSource(
                "jdbc:mysql://localhost:3306/test",
                "test",
                "test"
        );
    }

}
```

# Spring LifeCycle

## InitializingBean interface

- method: `afterPropertiesSet`

## DisposableBean interface

- method: `destroy`

## BeanPostProcessor

```java
package com.li.hellospring2.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class BeanPostProcessorComponent implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("[" + beanName + "] PostProcessAfterInitialization");
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("[" + beanName + "] PostProcessBeforeInitialization");
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }
}
```

## lefe cycle of bean

1. constructor
2. @Autowired
3. BeanPostProcessor:`postProcessBeforeInitialization`
4. @PostConstruct
5. InitializingBean:`afterPropertiesSet`
6. @Bean `init`
7. BeanPostProcessor:`postProcessAfterInitialization`
8. @PreDestroy
9. DisposableBean: `destroy`
10. @Bean `destroy`
11. ioc container destroy

# SpringBootTest

```java
package com.li.hellospring2;

import com.li.hellospring2.bean.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IocTest {

    @Autowired
    User user;

    @Test
    public void userTest() {
        System.out.println(user);
    }
}
```

# AOP

## java dynamic proxy

- instance being proxyed must implement interface

```java
// DynamicProxy.java
package com.li.hellospring2.proxy.dynamic;

import java.lang.reflect.Proxy;

public class DynamicProxy {
    public static Object newProxyInstance(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    System.out.println("[" + method + "] : args: " + args);
                    return method.invoke(target, args);
                }
        );
    }
}
```

```java
CService proxy2 = (CService) DynamicProxy.newProxyInstance(cService);
System.out.

println(proxy2.mul(2, 3));
```

## Spring AOP

- add dependency

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```
### @Aspect
- use `@Aspect` to decorate component

```java
package com.li.hellospring2.aspect;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {

    @Before("execution(int *(int , int))")
    public void before() {
        System.out.println("before");
    }

    @After("execution(int *(int , int))")
    public void after() {
        System.out.println("after");
    }

    @AfterReturning("execution(int *(int , int))")
    public void afterReturning() {
        System.out.println("afterReturning");
    }

    @AfterThrowing("execution(int *(int , int))")
    public void afterThrowing() {
        System.out.println("afterThrowing");
    }
}
```

1. `execution`: 匹配方法、类、包
    - execution(modifier? ret-type declaring-type?name-pattern(param-pattern) throws-pattern?)
    - `execution(* com.xyz.service..*(..))`
2. `within`: 匹配指定类的任意方法，不能匹配接口
    - within(declaring-type)
    - `within(com.xyz.service.*)`
3. `this`: 匹配代理对象实例的类型
    - this(declaring-type)
    - `this(com.xyz.service..*)`
4. `target`: 匹配被AOP代理对象的目标对象实例类型
    - target(declaring-type)
    - `target(com.xyz.service.*)`
5. `args`: 匹配方法参数类型和数量，参数类型可以为指定类型及其子类(execution不匹配子类),也可以包括注解
    - args(param-pattern)
    - `args(java.io.Serializable)`
6. `bean`: 通过 bean 的 id 或名称匹配
    - bean(bean-name)
    - `bean(*Service)`
7. `@annotation`: 匹配方法是否含有注解

| 表达式匹配范围 | 	within | 	this | 	target |
|---------|---------|-------|---------|
| 接口	     | ✘	      | ✔	    | ✔       |
| 实现接口的类	 | ✔	      | 〇	    | ✔       |
| 不实现接口的类 | ✔	      | ✔	    | ✔       |

- execute order: `Before` -> `Target` -> `AfterReturning / AfterThrowing` -> `After`
- get target info:
```java
@AfterReturning(value = "execution(int *(int , int))", returning = "result")
public void afterReturning(JoinPoint joinPoint, Object result) {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    System.out.println("Method class:" + methodSignature.getDeclaringType());
    System.out.println("Method class name:" + methodSignature.getDeclaringTypeName());// get class name method 1
    System.out.println("method class name: " + joinPoint.getTarget().getClass().getName());// get class name method 2
    System.out.println("Method name: " + methodSignature.getName());
    System.out.println("method args: " + joinPoint.getArgs());
    System.out.println("result: " + result);
        
    System.out.println("afterReturning");
}

@AfterThrowing(value = "execution(int *(int , int))", throwing = "e")
public void afterThrowing(JoinPoint joinPoint, Throwable e) {// change Throwable to specified Exception to filte exception
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    System.out.println("Method name: " + methodSignature.getName());
    System.out.println("method args: " + joinPoint.getArgs());
    System.out.println("get exception: " + e.getMessage());
    System.out.println("afterThrowing");
}
```

### @PointCut
```java
@Component
@Aspect
public class LogAspect {

    @Pointcut("execution(int *(int , int))")
    private void pointcut() {
    }

    @Before("pointcut()")
    public void before() {
        System.out.println("before");
    }
}
```

### @Order(n)
- if multiple pointcuts set in one method: `Proxy1(Proxy2(instance))`
- set order to **_Proxy class_** by decorator `@Order(n)`
- the **_smaller_** `n` is, the **_outer_** this proxy will be, the **_earlier_** its `@Before` will be called
- default `n`: `MAX_VALUE = 0x7fffffff`
- 