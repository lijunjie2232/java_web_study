<!-- TOC -->
* [Start a Spring MVC Application](#start-a-spring-mvc-application)
  * [maven dependency](#maven-dependency)
  * [A Simple Controller](#a-simple-controller)
    * [`@RequestMapping`](#requestmapping)
    * [`@Controller`](#controller)
    * [`@ResponseBody`](#responsebody)
    * [`@RestController`](#restcontroller)
    * [Start Application](#start-application)
    * [url match pattern:](#url-match-pattern)
    * [params:](#params)
* [SpringMVC argument resolver](#springmvc-argument-resolver)
  * [direct get request parameter by setting method parameter](#direct-get-request-parameter-by-setting-method-parameter)
  * [`@RequestParam`](#requestparam)
  * [use POJO to get parameter](#use-pojo-to-get-parameter)
  * [`@RequestHeader`](#requestheader)
  * [`@CookieValue`](#cookievalue)
  * [POJO for Complex HTML Form](#pojo-for-complex-html-form)
    * [form](#form)
    * [example request data](#example-request-data)
    * [Entity](#entity)
    * [handle](#handle)
    * [Output](#output)
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

### `@RequestMapping`

- `@RequestMapping` is used to map the URL to the controller method.

### `@Controller`

- `@Controller` is used to mark the class as a controller.

### `@ResponseBody`

- `@ResponseBody` is used to mark the method return value as the response body instead of returning a view.

### `@RestController`

- `@RestController` = `@Controller` + `@ResponseBody`

### Start Application

run `HelloSpringMvcApplication` to start application

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
    - `@RequestMapping(consumes = "application/json")` must contain the header `Content-Type` with value
      `application/json` and must be a json format request body
- `produces`:
    - `@RequestMapping(produces = "application/json")` specify the response content type

# SpringMVC argument resolver

## direct get request parameter by setting method parameter

- bind the request parameter to the method parameter.
- if not contain the parameter in the request, the method parameter will be null but boolean type will be false
- !!! used to bind the **_request parameter_** to the method parameter, not to bind the request body
  to the method parameter
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
- !!! *
  *_the alias `@RequestParam` specified must contain in the request if `required = false` is not set, or the method will
  return 400_**

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

## use POJO to get parameter

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

## `@RequestHeader`

- bind the request header to the method parameter

```java

@RequestMapping(value = "handle04")
public String handle04(
        @RequestHeader("host") String host,
        @RequestHeader("user-agent") String userAgent
) {
    System.out.println(host);
    System.out.println(userAgent);
    return "{\"msg\": \"ok\"}";
}
```

## `@CookieValue`

- bind the request cookie to the method parameter

```java

@RequestMapping(value = "handle05")
public String handle05(@CookieValue("JSESSIONID") String sessionId) {
    System.out.println(sessionId);
    return "{\"msg\": \"ok\"}";
}
```

## POJO for Complex HTML Form

### form

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="/css/index.css">
    <!-- 引入 Bootstrap CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<form action="/handle06" method="post">
    <div class="form-group">
        <label for="username">Username</label>
        <input type="text" id="username" name="username" placeholder="Username" class="form-control"></input>
    </div>
    <div class="form-group">
        <label for="password">Password</label>
        <input type="password" id="password" name="password" placeholder="Password" class="form-control"></input>
    </div>

    <!--  single check sex-->
    <div class="form-group">
        <label>Sex</label>
        <div class="form-check">
            <input type="radio" id="sex_male" name="sex" value="male" class="form-check-input"></input>
            <label class="form-check-label" for="sex_male">Male</label>
        </div>
        <div class="form-check">
            <input type="radio" id="sex_female" name="sex" value="female" class="form-check-input"></input>
            <label class="form-check-label" for="sex_female">Female</label>
        </div>
        <div class="form-check">
            <input type="radio" id="sex_other" name="sex" value="other" class="form-check-input"></input>
            <label class="form-check-label" for="sex_other">Other</label>
        </div>
    </div>

    <!--  select grade -->
    <div class="form-group">
        <label for="grade">Grade</label>
        <select id="grade" name="grade" class="form-control">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
        </select>
    </div>

    <!--  Address field -->
    <div class="form-group">
        <div class="form-group">
            <label for="province">Province</label>
            <select id="province" name="address.province" class="form-control" onchange="updateCities()">
                <option value="">Select Province</option>
                <option value="Tokyo">Tokyo</option>
                <option value="Kyoto">Kyoto</option>
                <option value="Osaka">Osaka</option>
            </select>
        </div>
        <div class="form-group">
            <label for="city">City</label>
            <input type="text" id="city" name="address.city" placeholder="City" class="form-control"></input>
        </div>
    </div>

    <!--  multi check favorite-->
    <div class="form-group">
        <label>Favorite</label>
        <div class="form-check">
            <label class="form-check-label">
                <input type="checkbox" name="favorite" value="football" class="form-check-input"></input>
                Football
            </label>
        </div>
        <div class="form-check">
            <label class="form-check-label">
                <input type="checkbox" name="favorite" value="basketball" class="form-check-input"></input>
                Basketball
            </label>
        </div>
        <div class="form-check">
            <label class="form-check-label">
                <input type="checkbox" name="favorite" value="swimming" class="form-check-input"></input>
                Swimming
            </label>
        </div>
    </div>
    <input type="submit" value="提交" class="btn btn-success"></input>
</form>
</body>
</html>
```

### example request data

- form data: (`username=123&password=321&sex=male&grade=1&address.province=Kyoto&address.city=a&favorite=football&favorite=swimming`)
    - username: 123
    - password: 321
    - sex: male
    - grade: 1
    - address.province: Kyoto
    - address.city: a
    - favorite: football
    - favorite: swimming

### Entity

```java
package com.li.hellospringmvc1.bean;

import lombok.Data;

@Data
public class Handle06Form {
    //    username=123&password=321&sex=male&grade=1&address.province=Kyoto&address.city=a&favorite=football&favorite=swimming
    private String username;
    private String password;
    private String sex;
    private int grade;
    private Address address;
    private String street;
    private String zipCode;
    private String[] favorite;
}

@Data
class Address {
    private String province;
    private String city;
}
```

### handle

```java

@RequestMapping(value = "handle06")
public String handle06(Handle06Form form) {
    System.out.println("-------- form --------");
    System.out.println(form);
    System.out.println("-------- form --------");
    return "{\"msg\": \"ok\"}";
}
```

### Output

```
-------- form --------
Handle06Form(username=123, password=321, sex=male, grade=1, address=Address(province=Kyoto, city=a), street=null, zipCode=null, favorite=[football, swimming])
-------- form --------
```

## `@RequestBody`
- receive json data binding to POJO
```java
@RequestMapping(value = "handle07")
//    receive json data
public String handle07(@RequestBody Handle06Form form) {
    System.out.println(form);
    return "{\"msg\": \"ok\"}";
    }
```
- test json data
```json
{
    "username": "123",
    "password": "321",
    "sex": "male",
    "grade": "2",
    "address": {
        "province": "Tokyo",
        "city": "a"
    },
    "favorite": [
        "football",
        "swimming"
    ]
}
```

- output:
```
Handle06Form(username=123, password=321, sex=male, grade=2, address=Address(province=Tokyo, city=a), street=null, zipCode=null, favorite=[football, swimming])
```

## File Upload
### Form
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>$Title$</title>
    <!-- 引入 Bootstrap CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<form action="/handle08" method="post" enctype="multipart/form-data">
    <div class="form-group">
        <label for="name">Name</label>
        <input type="text" class="form-control" id="name" name="name" placeholder="Enter your name">
    </div>
    <!--  single file upload-->
    <div class="form-group">
        <label for="file">File</label>
        <input type="file" class="form-control-file" id="file" name="file">
    </div>

    <!--  multi file upload-->
    <div class="form-group">
        <label for="files">Files</label>
        <input type="file" class="form-control-file" id="files" name="files" multiple></input>
    </div>
    <button type="submit" class="btn btn-primary">Upload</button>
</form>
</body>
</html>
```

### handle
```java
// get tmp dir path from spring boot application.properties, default is ./tmp
@Value("${tmp.path}")
private File tmpPath;

// file upload
@RequestMapping(value = "handle08")
public String handle08(
        @RequestParam("name") String name,
        @RequestParam("file") MultipartFile file,
        @RequestParam("files") MultipartFile[] files
) throws IOException {
  System.out.println(name);
  System.out.println(file.getOriginalFilename());

  // write file to tmp dir
  FileUtil.multipartFileWriter(file, tmpPath, true);

  System.out.println(files.length);
  for (MultipartFile multipartFile : files) {
    System.out.println(multipartFile.getOriginalFilename());
    FileUtil.multipartFileWriter(multipartFile, tmpPath, true);
  }
  return "{\"msg\": \"ok\"}";
}
```

```java
// FileUtil.multipartFileWriter

// static method for write MultipartFile file to tmp dir
public static void multipartFileWriter(MultipartFile file, File tmpDir, boolean overwrite) throws IOException {
    if (!tmpDir.exists()) {
        tmpDir.mkdirs();
    }
    File tmpFile = new File(tmpDir, Objects.requireNonNull(file.getOriginalFilename()));
    if (!tmpFile.exists() || overwrite) {
        file.transferTo(tmpFile);
    }
}
```

### spring file upload option
- `spring.servlet.multipart.enabled=true`: enable file upload
- `spring.servlet.multipart.file-size-threshold=0`: support file upload in disk
- `spring.servlet.multipart.max-file-size=10MB`: max file size in bytes
- `spring.servlet.multipart.max-request-size=10MB`: max request size in bytes
- `spring.servlet.multipart.location=/tmp`: file upload tmp dir
- `spring.servlet.multipart.resolve-lazily=true`: resolve file upload lazily

## HttpEntity
- `HttpEntity` is a generic interface for HTTP requests and responses. It provides a way to access the request or response body as a byte array, a String, or a stream.
```java
@RequestMapping(value = "handle09")
public String handle09(
//        HttpEntity<String> entity
        HttpEntity<Handle06Form> entity
){
    System.out.println(entity.getBody());
    System.out.println(entity.getHeaders());
    return "{\"msg\": \"ok\"}";
}
```

### Request Body
```json
{
    "username": "123",
    "password": "321",
    "sex": "male",
    "grade": "2",
    "address": {
        "province": "Tokyo",
        "city": "a"
    },
    "favorite": [
        "football",
        "swimming"
    ]
}
```

### Output
```
Handle06Form(username=123, password=321, sex=male, grade=2, address=Address(province=Tokyo, city=a), street=null, zipCode=null, favorite=[football, swimming])
[cookie:"JSESSIONID=123321; AUTH=111111;", user-agent:"PostmanRuntime/7.43.0", accept:"*/*", host:"127.0.0.1:8080", accept-encoding:"gzip, deflate, br", connection:"keep-alive", content-length:"235", Content-Type:"application/json;charset=UTF-8"]
```

## Servlet API
```java
// Servlet API Test
@RequestMapping(value = "handle10")
public void handle10(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // print json request body
    System.out.println(request.getReader().lines().reduce("", (acc, cur) -> acc + cur));
    response.getWriter().write("{\"msg\": \"ok\"}");
}
```