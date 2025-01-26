<!-- TOC -->
* [Spring Container](#spring-container)
  * [ConfigurableApplicationContext](#configurableapplicationcontext)
    * [start up of ioc](#start-up-of-ioc)
    * [@Bean](#bean)
    * [getBean](#getbean)
  * [@Configuration](#configuration)
  * [Spring MVC Annotation](#spring-mvc-annotation)
  * [@Import](#import)
  * [@Scope](#scope)
  * [@Lazy](#lazy)
  * [FactoryBean](#factorybean)
  * [@Condition](#condition)
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
      * [get target info:](#get-target-info)
    * [@PointCut](#pointcut)
    * [@Order(n)](#ordern)
    * [@Around](#around)
* [Spring Utils](#spring-utils)
  * [AnnotationUtils](#annotationutils)
  * [ClassUtils](#classutils)
  * [TypeUtils](#typeutils)
  * [ReflectionUtils](#reflectionutils)
  * [Base64Utils](#base64utils)
  * [SerializationUtils](#serializationutils)
  * [BeanUtils](#beanutils)
* [IOC: DefaultSingletonBeanRegistry.getSingleton](#ioc-defaultsingletonbeanregistrygetsingleton)
* [SpringBoot DataSource](#springboot-datasource)
* [Transaction](#transaction)
  * [@Transactional](#transactional)
* [Spring Conclusion](#spring-conclusion)
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
      // method 1
      ConfigurableApplicationContext ioc = SpringApplication.run(HelloSpring2Application.class, args);
      System.out.println(ioc);
      // method 2
//        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:ioc.xml");


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

### start up of ioc
```java
// org.springframework.context.support.ClassPathXmlApplicationContext
// .ClassPathXmlApplicationContext(java.lang.String[], boolean, org.springframework.context.ApplicationContext)
public ClassPathXmlApplicationContext(String[] configLocations, boolean refresh, @Nullable ApplicationContext parent) throws BeansException {
    super(parent);
    this.setConfigLocations(configLocations);
    if (refresh) {
        this.refresh();
    }

}
```
```java
// org.springframework.context.support.AbstractApplicationContext.refresh
public void refresh() throws BeansException, IllegalStateException {
    this.startupShutdownLock.lock();

    try {
        this.startupShutdownThread = Thread.currentThread();
        StartupStep contextRefresh = this.applicationStartup.start("spring.context.refresh");
        this.prepareRefresh();
        ConfigurableListableBeanFactory beanFactory = this.obtainFreshBeanFactory();
        this.prepareBeanFactory(beanFactory);

        try {
            this.postProcessBeanFactory(beanFactory);
            StartupStep beanPostProcess = this.applicationStartup.start("spring.context.beans.post-process");
            this.invokeBeanFactoryPostProcessors(beanFactory);
            this.registerBeanPostProcessors(beanFactory);
            beanPostProcess.end();
            this.initMessageSource();
            this.initApplicationEventMulticaster();
            this.onRefresh();
            this.registerListeners();
            this.finishBeanFactoryInitialization(beanFactory);
            this.finishRefresh();
        } catch (Error | RuntimeException var12) {
            if (this.logger.isWarnEnabled()) {
                this.logger.warn("Exception encountered during context initialization - cancelling refresh attempt: " + var12);
            }

            this.destroyBeans();
            this.cancelRefresh(var12);
            throw var12;
        } finally {
            contextRefresh.end();
        }
    } finally {
        this.startupShutdownThread = null;
        this.startupShutdownLock.unlock();
    }

}
```
1. `prepareRefresh`: init bean post processor
2. `obtainFreshBeanFactory`: create bean factory
3. `prepareBeanFactory`: init bean factory
4. `postProcessBeanFactory`: post process bean factory (for by sub class)
5. `invokeBeanFactoryPostProcessors`: invoke factory post processors registed as beans in the context
    - `BeanFactoryPostProcessor`: post processor for bean factory
6. `registerBeanPostProcessors`: register bean processors that intercept bean creation process (get all names of bean post processor in ioc)
   1. get all bean post processors name
   2. `ioc.getBean` by name, if bean not created, it will tregger bean creation process
7. `initMessageSource`: support internationalization by init `MessageSource` component (singleton)
8. `initApplicationEventMulticaster`: support publish event by init `ApplicationEventMulticaster` component (singleton)
9. `onRefresh`: leave for sub class to do somethin
10. `registerListeners`: register listener into `ApplicationEventMulticaster` (get `ApplicationListener` from ioc)
11. `finishBeanFactoryInitialization`: init all remaining beans in ioc container which is not to be lazy loaded
12. `finishRefresh`: publish `ContextRefreshedEvent`

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

## @Condition

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
#### get target info:

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

### @Around
- around should throw errors to upper wrapper in catch block
```java
@Order
@Component
@Aspect
public class LogAspect {

    @Pointcut("execution(int *(int , int))")
    private void pointcut() {
    }

    @Around("pointcut()")
    public Object Around(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        System.out.println("around args: " + Arrays.toString(args));
        Object result = null;
        try {
            System.out.println("around before");
            result = pjp.proceed(args);
            System.out.println("around after returning");
        } catch (Exception e) {
            System.out.println("around after throwing");
            throw e;
        } finally {
            System.out.println("around after");
        }
        return result;
    }

}
```

# Spring Utils

## AnnotationUtils

- `findAnnotation`: 获取Class/Method上面标注的注解
    ```java
    // 获取Class上面的注解
    RequestMapping requestMapping = AnnotationUtils.findAnnotation(MainController.class, RequestMapping.class);
    System.out.println(requestMapping);
    System.out.println(Arrays.toString(requestMapping.value()));
    
    // 获取Method上面的注解
    Method carMethod = ReflectionUtils.findMethod(MainController.class, "car");
    Bean bean = AnnotationUtils.findAnnotation(carMethod, Bean.class);
    System.out.println(Arrays.toString(bean.name()));
    ```
- `getAnnotationAttributes`: 获取annotation的所有属性
    ```java
    RequestMapping requestMapping = AnnotationUtils.findAnnotation(MainController.class, RequestMapping.class);
    // 获取 RequestMapping注解的所有属性
    Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(requestMapping);
    System.out.println(annotationAttributes);
    ```
- `isAnnotationDeclaredLocally`: 验证annotation是否直接注释在类上而不是集成来的
    ```java
    boolean isExistsRequestMapping = AnnotationUtils.isAnnotationDeclaredLocally(RequestMapping.class, MainController.class);
    System.out.println(isExistsRequestMapping);
    
    boolean isExistsMapping = AnnotationUtils.isAnnotationDeclaredLocally(Mapping.class, MainController.class);
    System.out.println(isExistsMapping);
    ```

## ClassUtils

- `getAllInterfaces`: 获取对象的所有接口
    ```java
    Class<?>[] allInterfaces = ClassUtils.getAllInterfaces(new User());
    ```
- `getPackageName`: 获取某个类的包名
    ```java
    String packageName = ClassUtils.getPackageName(User.class);
    System.out.println(packageName);
    ```
- `isInnerClass`: 判断某个类是否内部类
    ```java
    System.out.println(ClassUtils.isInnerClass(User.class));
    ```
- `isCglibProxy`: 判断对象是否代理对象
    ```java
    System.out.println(ClassUtils.isCglibProxy(new User()));
    ```

## TypeUtils

- `isAssignable`: 依照 Java 泛型規則檢查右側類型是否能转换为左側類型
    ```java
    ClassUtils.isAssignable(List.class, ArrayList.class)
    ```

## ReflectionUtils

- `findMethod`: 获取方法
    ```java
    Method method = ReflectionUtils.findMethod(User.class, "getId");
    ```
- `findField`: 获取字段
    ```java
    Field field = ReflectionUtils.findField(User.class, "id");
    ```
- `invokeMethod`: 执行方法
    ```java
    Method method = ReflectionUtils.findMethod(User.class, "getId");
    ReflectionUtils.invokeMethod(method, springContextsUtil.getBean(User.class), param);
    ```
- `isPublicStaticFinal`: 判断字段是否常量
    ```java
    Field field = ReflectionUtils.findField(User.class, "id");
    System.out.println(ReflectionUtils.isPublicStaticFinal(field));
    ```
- `doWithFields`: 获取类的所有字段
    ```java
    ReflectionUtils.doWithFields(xxx.class, field -> {
        System.out.println("Field: " + field.getName());
    });
    ```
- `isEqualsMethod`: 判断是否equals方法
    ```java
    Method method = ReflectionUtils.findMethod(User.class, "getId");
    System.out.println(ReflectionUtils.isEqualsMethod(method));
    ```

## Base64Utils

- 包含encode和decode方法，用于对数据进行编码和解码
    ```java
    String str = "abc";
    String encode = new String(Base64Utils.encode(str.getBytes()));
    System.out.println("编码后：" + encode);
    try {
        String decode = new String(Base64Utils.decode(encode.getBytes()), "utf8");
        System.out.println("解码：" + decode);
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
    ```

## SerializationUtils

- 序列化和反序列化功能
    ```java
    Map<String, String> map = Maps.newHashMap();
    map.put("a", "1");
    map.put("b", "2");
    map.put("c", "3");
    byte[] serialize = SerializationUtils.serialize(map);
    Object deserialize = SerializationUtils.deserialize(serialize);
    System.out.println(deserialize);
    ```

## BeanUtils

- `copyProperties`: 拷贝对象的属性
    ```java
    User user1 = new User();
    user1.setId(1L);
    user1.setName("苏三说技术");
    user1.setAddress("成都");
    
    User user2 = new User();
    BeanUtils.copyProperties(user1, user2);
    System.out.println(user2);
    ```
- `instantiateClass`: 实例化某个类
    ```java
    User user = BeanUtils.instantiateClass(User.class);
    System.out.println(user);
    ```
- `findDeclaredMethod`: 获取指定类的指定方法
    ```java
    Method declaredMethod = BeanUtils.findDeclaredMethod(User.class, "getId");
    System.out.println(declaredMethod.getName());
    ```
- `findPropertyForMethod`: 获取指定方法的参数
    ```java
    Method declaredMethod = BeanUtils.findDeclaredMethod(User.class, "getId");
    PropertyDescriptor propertyForMethod = BeanUtils.findPropertyForMethod(declaredMethod);
    System.out.println(propertyForMethod.getName());
    ```

# IOC: DefaultSingletonBeanRegistry.getSingleton
- set `spring.main.allow-circular-references=true` to enable circular reference
1. find bean
    ```
    1. singletonObject = singletonObjects.get(beanName);
                 ||
                 \/
    2. singletonObject = earlySingletonObjects.get(beanName);
    ```
2. find or get bean with synchronized block
- `singletonObjects` is an object pool
- `singletonObjects`: `Map<String, Object>`
- `earlySingletonObjects`: `Map<String, Object>`
- `singletonFactories`: `Map<String, ObjectFactory<?>>`
    ```
    1. singletonObject = singletonObjects.get(beanName);
                 ||
                 \/
    2. singletonObject = earlySingletonObjects.get(beanName);
                 ||
                 \/
    3. xxxFactory = singletonFactories.get(beanName);
       singletonObject = xxxFactory.getObject();
       earlySingletonObjects.put(beanName, singletonObject);
    ```

# SpringBoot DataSource
- add dependency to pom
    ```xml
    <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-jdbc -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
        <version>3.4.0</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/com.mysql/mysql-connector-j -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>9.1.0</version>
    </dependency>
    ```
- add sql config to "properties file"
    ```properties
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.datasource.url=jdbc:mysql://localhost:13306/ssm1
    spring.datasource.username=root
    spring.datasource.password=root
    ```
- get DataSource (default hikari) by @Autowired

# Transaction
1. add `@EnableTransactionManagement` to spring application class
2. add `@Transactional` to method to enable transaction on this method

```java
package com.li.hellospring2.service.impl;

import com.li.hellospring2.bean.Account;
import com.li.hellospring2.bean.Book;
import com.li.hellospring2.bean.Order;
import com.li.hellospring2.dao.AccountDao;
import com.li.hellospring2.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class AccountServiceImpl {
    @Autowired
    BookDao bookDao;
    @Autowired
    AccountDao accountDao;

    /**
     * check oud method
     *
     * @param order:Order order
     */
    @Transactional
    public boolean checkout(Order order) {
        try {
            BigDecimal totalprice = new BigDecimal(0);
            Account account = accountDao.getAccountById(order.getAcccountId());
            for (Map.Entry<Integer, Integer> entry : order.getOrderMap().entrySet()) {
                Integer bid = entry.getKey();
                Book book = bookDao.getBookById(bid);
                assert book.getStock() >= entry.getValue();
                totalprice = totalprice.add(book.getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
                assert account.getBalance().compareTo(totalprice) >= 0;
            }
            accountDao.updateBalance(account.getId(), totalprice.multiply(new BigDecimal(-1)));
            for (Map.Entry<Integer, Integer> entry : order.getOrderMap().entrySet()) {
                bookDao.updateBookStock(entry.getKey(), -entry.getValue());
            }
            int a = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
        return true;
    }
}
```

## @Transactional
- `transactionManager/value(alias of transactionManager`: transactionManager, default is `JdbcTransactionManager`
  1. `TransactionManager`: control commit/rollback of transaction
  2. `TransactionInterceptor`: control when to commit/rollback, TransactionInterceptor is an aspect
     1. `completeTransactionAfterThrowing(txInfo, ex)` -> `rollbackOn(ex)`
     2. `completeTransactionAfterReturning(txInfo)` -> `commitTransactionAfterReturning(txInfo)`
- `propagation`: propagation behavior of transaction, default is `Propagation.REQUIRED`
  - `Required`: if no transaction exists, create a new transaction, otherwise use the current transaction
  - `Supports`: if a transaction exists, use the current transaction, otherwise run without transaction
  - `Mandatory`: if a transaction exists, use the current transaction, otherwise throw an exception
  - `Requires_New`: pause existing transaction and create a new transaction
  - `Nested`: if no transaction exists, create a new transaction, otherwise use the current transaction to create a nested transaction
  - `Not_Supported`: (no transaction) if a transaction exists, pause the current transaction, then/otherwise run without transaction
  - `Never`: (no transaction) if a transaction exists, throw an exception
- `isolation`: isolation level of transaction
- `timeout`: timeout of transaction (second)
  - timeout is from the first **_db operation_** to commit (the last db operation) instead of from the first line of method code to the end
- `readOnly`: if db operation contains only read operation, set `readOnly=true` to enable read-only transaction which could optimize performance
- `rollbackFor`/`rollbackForClassName`: specify **_additional_** exception class to rollback, all `RuntimeException` could already be rolled back by default
- `noRollbackFor` / `noRollbackForClassName`: specify exception class not to trigger rollback

- settings of outer method decide inner method's transaction limit if inner method propagation is `REQUIRED`

# Spring Conclusion
1. Spring 容器 (Spring Container)
- ConfigurableApplicationContext: 构建容器时构造 Bean，默认所有 Bean 都是单例（除 prototype 范围的 Bean）。
   提供了 getBean 方法来获取 Bean，支持通过类、名称或同时使用名称和类来获取。
- @Bean: 用于将方法返回的对象注册为 Spring 容器中的 Bean。
- @Configuration: 标记配置类，该类本身也是一个 Bean。
- FactoryBean: 实现 FactoryBean 接口，提供自定义的 Bean 创建逻辑。
- @Condition: 实现条件化创建 Bean 的逻辑。

2. 依赖注入 (Inject)
- @Autowired: 自动装配依赖，优先按类型查找，其次按名称查找。可以设置 required=false 允许为空。
- @Qualifier: 指定特定名称的 Bean 进行注入。
- @Primary: 标记一个 Bean 作为首选注入对象。
- @Resource: 类似于 @Autowired，但它是 Java 的通用接口，不允许为空。
- 构造函数注入与 Setter 注入: 支持通过构造函数或 Setter 方法进行依赖注入。
- Aware 接口: 提供对容器环境、资源加载器等的访问。
- @Value: 直接注入值或从属性文件中读取值。
- @PropertySource: 指定属性文件的位置。
- Profile: 根据激活的环境选择不同的 Bean。

3. Spring 生命周期 (Spring LifeCycle)
- InitializingBean 和 DisposableBean: 分别在初始化后和销毁前执行特定逻辑。
- BeanPostProcessor: 在 Bean 初始化前后进行处理。
- 生命周期顺序: 
  - 构造函数
  - 依赖注入
  - postProcessBeforeInitialization
  - @PostConstruct
  - afterPropertiesSet
  - @Bean init
  - postProcessAfterInitialization
  - @PreDestroy
  - destroy

4. Spring 测试 (SpringBootTest)
- @SpringBootTest: 用于集成测试class，自动配置应用程序上下文。

5. 面向切面编程 (AOP)
- Java 动态代理: 适用于实现接口的类。
- Spring AOP: 基于 AspectJ 注解实现 AOP，如 @Aspect、@PointCut、@Before、@After、@Around 等。
- 表达式语言: 使用 execution、within、this、target、args、bean 和 @annotation 等表达式匹配目标方法。
- @Order: 控制多个切面的执行顺序。

6. Spring 工具类 (Spring Utils)
- AnnotationUtils: 提供注解相关的工具方法，如查找注解、获取注解属性等。
- ClassUtils: 提供类相关的工具方法，如获取接口、包名、判断内部类等。
- TypeUtils: 提供类型转换相关的工具方法。
- ReflectionUtils: 提供反射相关的工具方法，如查找方法、字段、调用方法等。

7. Spring 事务(Spring Transaction)
- @EnableTransactionManagement: 开启事务管理，默认使用 JdbcTransactionManager 作为事务管理器。
- @Transactional: 用于标注在方法或类上，用于开启事务，默认的传播行为是 REQUIRED，默认的隔离级别是 DEFAULT。
