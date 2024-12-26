
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

