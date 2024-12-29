<!-- TOC -->
* [Start a Spring MVC Application](#start-a-spring-mvc-application)
  * [maven dependency](#maven-dependency)
  * [A Simple Controller](#a-simple-controller)
  * [Start Application](#start-application)
  * [@RequestMapping](#requestmapping)
    * [url match pattern:](#url-match-pattern)
    * [params:](#params)
* [](#)
<!-- TOC -->


# Start a Spring MVC Application
## maven dependency
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-tomcat</artifactId>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

## A Simple Controller

- `@RestController` = `@Controller` + `@ResponseBody`

```java
package com.li.hellospringmvc1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

// method 1
@ResponseBody
@Controller
// method 2
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String handle() {
        return "hello spring mvc";
    }
}
```

## Start Application
run `HelloSpringMvcApplication` to start application

## @RequestMapping
`@RequestMapping` is used to map the URL to the controller method.

### url match pattern:
  - `*`: match any character in one path level
  - `**`: match any number of path level, should put at the end
  - `?`: match one character in one path level

### params:
- `method`:
  - `@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})`
- `params`: 
  - `@RequestMapping(params = "id")` must contain the parameter `id` in the request
  - `@RequestMapping(params = "!id")` must not contain the parameter `id` in the request
  - `@RequestMapping(params = "id=1")` must contain the parameter `id` with value `1`
  - `@RequestMapping(params = "id!=1")` must not contain the parameter `id` with value `1`
- `headers`:
  - `@RequestMapping(headers = "Content-Type")` must contain the header `Content-Type`
- `consumes`:
  - `@RequestMapping(consumes = "application/json")` must contain the header `Content-Type` with value `application/json` and must be a json format request body
- `produces`:
  - `@RequestMapping(produces = "application/json")` specify the response content type

# SpringMVC argument resolver
## `@RequestParam`
- `@RequestParam` is used to bind the request parameter to the method parameter.
- if not contain the parameter in the request, the method parameter will be null but boolean type will be false
- !!! `@RequestParam` is used to bind the **_request parameter_** to the method parameter, not to bind the request body to the method parameter
- !!! **_name of the method parameter must be the same as the request parameter_**
```java
@RequestMapping("handle01")
public String handle01(String username, String password, boolean accept) {
  System.out.println("username = " + username);
  System.out.println("password = " + password);
  System.out.println("accept = " + accept);
  return "{\"msg\": \"ok\"}";
}
```
## `@RequestParam`
- `@RequestParam` is used to decorate the method parameter with an alias name.
- `@RequestParam` is used to bind the request parameter to the method parameter with an alias name.
- `defaultValue` specify the default value of the method parameter if the request parameter is not contained
- !!! **_the alias `@RequestParam` specified must contain in the request if `required = false` is not set, or the method will return 400_**
```java
@RequestMapping("handle02")
public String handle02(
        @RequestParam("username") String user,
        @RequestParam(defaultValue = "") String password,
        @RequestParam(value = "agree", required = false) boolean accept) {
    System.out.println("username = " + user);
    System.out.println("password = " + password);
    System.out.println("accept = " + accept);
    return "{\"msg\": \"ok\"}";
}
```

## POJO argument
- bind an entity in method parameter with request parameter
```java
@Data
public class User {
  private String username;
  private String password = "";
  private String email;
}
```
```java
@RequestMapping(value = "handle03")
public String handle03(User user) {
    System.out.println(user);
    return "{\"msg\": \"ok\"}";
}
```