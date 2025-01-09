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
* [SpringMVC Request](#springmvc-request)
  * [direct get request parameter by setting method parameter](#direct-get-request-parameter-by-setting-method-parameter)
  * [`@RequestParam`](#requestparam)
  * [use POJO to get parameter](#use-pojo-to-get-parameter)
  * [`@PathVariable`](#pathvariable)
  * [`@RequestHeader`](#requestheader)
  * [`@CookieValue`](#cookievalue)
  * [POJO for Complex HTML Form](#pojo-for-complex-html-form)
    * [form](#form)
    * [example request data](#example-request-data)
    * [Entity](#entity)
    * [handle](#handle)
    * [Output](#output)
  * [`@RequestBody`](#requestbody)
  * [File Upload](#file-upload)
    * [Form](#form-1)
    * [handle](#handle-1)
    * [spring file upload option](#spring-file-upload-option)
  * [HttpEntity](#httpentity)
    * [Request Body](#request-body)
    * [Output](#output-1)
  * [Servlet API](#servlet-api)
  * [SpringMVC Request 总结](#springmvc-request-总结)
    * [RequestMapping 函数接收参数类型:](#requestmapping-函数接收参数类型)
    * [@RequestPart和 @RequestParam的区别](#requestpart和-requestparam的区别)
* [SpringMVC Response](#springmvc-response)
  * [Response json data by bean](#response-json-data-by-bean)
  * [File download](#file-download)
    * [File Name Encode](#file-name-encode)
    * [Continuous Download for Large File](#continuous-download-for-large-file)
      * [Parse of `Range` in Header](#parse-of-range-in-header)
      * [Use OutputStream of Response](#use-outputstream-of-response)
      * [Use `InputStreamResource` (return `ResponseEntity<InputStreamResource>`)](#use-inputstreamresource-return-responseentityinputstreamresource)
  * [Thymeleaf](#thymeleaf)
    * [dependency](#dependency)
    * [a simple example](#a-simple-example)
    * [expression](#expression)
    * [inner object](#inner-object)
    * [value](#value)
    * [iterate](#iterate)
    * [conditional](#conditional)
    * [local variable](#local-variable)
    * [inline expression](#inline-expression)
    * [template fragment](#template-fragment)
* [RESTful](#restful)
  * [An Example of RESTful API](#an-example-of-restful-api)
* [Spring Filter](#spring-filter)
* [Spring Exception](#spring-exception)
  * [`@ExceptionHandler`](#exceptionhandler)
    * [Example](#example)
  * [`@ControllerAdvice`](#controlleradvice)
    * [Example](#example-1)
  * [Exception handling in Project](#exception-handling-in-project)
    * [a simple business exception class example](#a-simple-business-exception-class-example)
      * [a enum class to store error code and message](#a-enum-class-to-store-error-code-and-message)
      * [a business exception class](#a-business-exception-class)
      * [register the exception handler](#register-the-exception-handler)
      * [throw the exception in service](#throw-the-exception-in-service)
* [Spring Data Validation](#spring-data-validation)
  * [`BindingResult`: get errors of `@Valid`](#bindingresult-get-errors-of-valid)
  * [global exception handler for `@Valid`](#global-exception-handler-for-valid)
  * [Custom Validation](#custom-validation)
* [Internationalization (i18n)](#internationalization-i18n)
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

# SpringMVC Request

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

## `@PathVariable`

```java

@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
public Result getEmployee(@PathVariable int id) {
    return new Result(employeeService.getEmployee(id));
}
```

- request path pattern:
    - `@RequestMapping("/employee/{id}")`
    - `@RequestMapping("/employee/{*id}")`: /employee/1/2/3 -> id = 1/2/3
    - `@RequestMapping("/res/{filename:\\w+}.dat")`: filename should satisfy the regular expression `\w+`

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

- form data: (
  `username=123&password=321&sex=male&grade=1&address.province=Kyoto&address.city=a&favorite=football&favorite=swimming`)
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

- `HttpEntity` is a generic interface for HTTP requests and responses. It provides a way to access the request or
  response body as a byte array, a String, or a stream.

```java

@RequestMapping(value = "handle09")
public String handle09(
//        HttpEntity<String> entity
        HttpEntity<Handle06Form> entity
) {
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

## SpringMVC Request 总结

### RequestMapping 函数接收参数类型:

1. WebRequest / NativeWebRequest: 使用非原生的Servlet API获取request
2. ServletRequest / HttpServletRequest: 使用原生的 Servlet API
3. HttpSession: 获取session对象
4. PushBuilder: 推送消息(HTTP/2)
5. Principal: 获取当前认证用户
6. HttpMethod: 获取当前请求方法
7. Locale: 获取当前请求的区域信息
8. TimeZone, ZoneId: 获取当前请求的时区信息
9. InputStream, Reader: 获取请求的输入流
10. OutputStream, Writer: 获取响应的输出流
11. @PathVariable: 获取url路径参数
12. @MatrixVariable: 获取矩阵变量
13. @RequestParam: 获取请求参数
14. @RequestHeader: 获取请求头
15. @CookieValue: 获取cookie值
16. @RequestBody: 获取请求体
17. HttpEntity<T>: 获取请求体和请求头
18. @RequestPart: 获取请求体中的文件
19. Map, Model, ModelMap: 服务端渲染共享数据
20. @ModelAttribute: 前置数据绑定
21. Errors, BindingResult: 数据校验结果
22. @SessionAttributes: 绑定session对象
23. UriCompnentsBuilder: 构建URI
24. @RequestAttribute: 请求域中的属性
25. 其他默认为@RequestParam的参数

### @RequestPart和 @RequestParam的区别

| 特性   | @RequestParam                                                | @RequestPart              |
|------|--------------------------------------------------------------|---------------------------|
| 用途   | 从 URL 查询参数或表单数据中提取单个值                                        | 从 multipart 请求中提取特定部分的数据  |
| 适用场景 | 简单表单数据和 URL 查询参数                                             | 包含文件上传和表单数据的 multipart 请求 |
| 数据类型 | 基本数据类型或对象                                                    | `MultipartFile` 对象或其他对象   |
| 请求类型 | ` application/x-www-form-urlencoded` 或 `multipart/form-data` | `multipart/form-data`     |

- 当请求为`multipart/form-data`时，`@RequestParam`只能接收`String`类型的name-value值，`@RequestPart`可以接收复杂的请求域（像
  `json`、`xml`）；`@RequestParam` 依赖Converter or PropertyEditor进行数据解析， `@RequestPart`参考'Content-Type' header，依赖
  `HttpMessageConverters`进行数据解析

    - 对于`multipart/form-data`类型的请求体，`@RequestPart~可以将jsonData的json数据转换为Person对象
      ```java
      @RequestMapping("jsonDataAndUploadFile")
      @ResponseBody
      public String jsonDataAndUploadFile(@RequestPart("uploadFile") MultiPartFile uploadFile,
                                          @RequestPart("jsonData") Person person) {
          StringBuilder sb = new StringBuilder();
          sb.append(uploadFile.getOriginalFilename()).append(";;;"));
          return person.toString() + ":::" + sb.toString();
      }
      ```

    - 对于`multipart/form-data`类型的请求体，`@RequestParam`对于jsonData的json数据只能用String字符串来接收
      ```java
      @RequestMapping("jsonDataAndUploadFile")
      @ResponseBody
      public String jsonDataAndUploadFile(@RequestPart("uploadFile") MultiPartFile uploadFile,
                                          @RequestParam("josnData") String jsonData) {
          StringBuilder sb = new StringBuilder();
          sb.append(uploadFile.getOriginalFilename()).append(";;;"));
          return person.toString() + ":::" + sb.toString();
      }
      ```

# SpringMVC Response

- `@ResponseBody`: 将返回值作为响应体返回，默认使用`HttpMessageConverter`转换器转换成响应体

## Response json data by bean

```java

@RequestMapping(value = "handle11")
public User handle11() {
    return new User("123", "321", "123@321.123");
}
```

## File download

- 设置响应状态为200 OK：`.ok()`, 表示请求成功。
- 添加响应头：`.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)`,
  设置Content-Disposition为attachment;filename=filename，提示浏览器以附件形式下载文件，并指定文件名。
- 设置内容类型：`.contentType(MediaType.APPLICATION_OCTET_STREAM)`, 使用application/octet-stream，表示二进制流数据。
- 设置内容长度：`.contentLength(bytes.length)`, 告知客户端文件的大小。
- 设置响应体：`.body(bytes)`, 将文件内容作为字节数组返回。

```java

@RequestMapping(value = "handle12")
public ResponseEntity<byte[]> handle12(String filename) {
    // 构建文件路径
    File file = new File(tmpPath, filename);
    System.out.println(file.getAbsoluteFile());

    // 检查文件是否存在
    if (!file.exists()) {
        return ResponseEntity.notFound().build();
    }
    try (FileInputStream fs = new FileInputStream(file)) {
        byte[] bytes = fs.readAllBytes();
        /*
        设置响应状态为200 OK：表示请求成功。
        添加响应头：设置Content-Disposition为attachment;filename=filename，提示浏览器以附件形式下载文件，并指定文件名。
        设置内容类型：使用application/octet-stream，表示二进制流数据。
        设置内容长度：告知客户端文件的大小。
        设置响应体：将文件内容作为字节数组返回。
         */
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(bytes.length)
                .body(bytes);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
```

### File Name Encode

- use `URLEncoder.encode(filename, StandardCharsets.UTF_8)` to encode file name

### Continuous Download for Large File

#### Parse of `Range` in Header

```java
public void downloadFile(@RequestParam String filename, HttpServletRequest request) throws IOException {

    // 获取请求头中的Range字段
    String rangeHeader = request.getHeader(HttpHeaders.RANGE);
    if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
        String[] rangeParts = rangeHeader.substring(6).split("-");
        start = Long.parseLong(rangeParts[0]);
        if (rangeParts.length > 1) {
            end = Long.parseLong(rangeParts[1]);
        }
    }

    // 检查请求的范围是否有效
    assert start > end || start >= fileSize

    // 设置响应头
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.setContentLength(end - start + 1);
    headers.setContentRange("bytes " + start + "-" + end + "/" + fileSize);
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

    // ...
}
```

#### Use OutputStream of Response

1. 获取文件长度：使用`file.length()`获取文件长度
2. 获取客户端请求文件起始位置： 从请求头中获取Range，并解析出起始位置和结束位置
3. 设置响应头：使用`response.setHeader("Content-Length", new String(end - start + 1))`设置响应头的Content-Length为文件长度等。
4. 设置响应体：使用`response.getOutputStream()`获取输出流
5. 读取数据：使用`FileInputStream.read(buffer, start, buffer.length)`从断点读取最长为缓冲区大小的文件内容到`buffer`
6. 写入数据：使用`response.getOutputStream().write(buffer, 0, bytesRead)`将读取的数据写入输出流
7. 刷新输出流：使用`response.getOutputStream().flush()`刷新输出流，确保所有数据都被写入输出流
8. 关闭输出流：使用`response.getOutputStream().close()`关闭输出流

```java
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
public class FileDownloadController {

    // 假设tmpPath是文件存储的临时目录
    private final File tmpPath = new File("./tmp");

    @GetMapping("/download")
    public void downloadFile(@RequestParam String filename, HttpServletResponse response) throws IOException {
        // 构建文件路径
        File file = new File(tmpPath, filename);
        System.out.println(file.getAbsolutePath());

        // 检查文件是否存在
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }

        // 设置响应头
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setContentLengthLong(file.length());

        // 获取输出流
        OutputStream outputStream = response.getOutputStream();

        // 使用FileInputStream读取文件内容，并写入输出流
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        // 刷新输出流
        outputStream.flush();

        // 关闭输出流
        outputStream.close();
    }
}

```

#### Use `InputStreamResource` (return `ResponseEntity<InputStreamResource>`)

1. 获取文件长度：使用`file.length()`获取文件长度
2. 获取客户端请求文件起始位置： 从请求头中获取Range，并解析出起始位置和结束位置
3. 初始化`ResponseEntity.ok()`并设置响应头
    - 使用`.header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileSize)`设置响应头的Content-Range为文件范围等
    - 使用
      `.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8))`
      设置响应头的Content-Disposition为附件形式下载，并指定文件名
    - 使用`.contentType(MediaType.APPLICATION_OCTET_STREAM)`设置响应头的Content-Type为二进制流数据
    - 使用`.contentLength(end - start + 1)`设置响应头的Content-Length为文件长度
4. 获取文件输入流资源： 使用`InputStreamResource resource = new InputStreamResource(new FileInputStream(file))`获取文件输入流资源
5. 将输入流资源封装为响应体：使用`.body(resource)`将输入流资源封装为响应体

```java
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
public class FileDownloadController {

    // 假设tmpPath是文件存储的临时目录
    private final File tmpPath = new File("./tmp");

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam String filename, HttpServletRequest request) throws IOException {
        // 构建文件路径
        File file = new File(tmpPath, filename);
        System.out.println(file.getAbsolutePath());

        // 检查文件是否存在
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        // 获取文件长度
        long fileSize = file.length();
        long start = 0;
        long end = fileSize - 1;

        // 获取请求头中的Range字段
        String rangeHeader = request.getHeader(HttpHeaders.RANGE);
        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            String[] rangeParts = rangeHeader.substring(6).split("-");
            start = Long.parseLong(rangeParts[0]);
            if (rangeParts.length > 1 && !rangeParts[1].isEmpty()) {
                end = Long.parseLong(rangeParts[1]);
            }
        }

        // 检查请求的范围是否有效
        if (start > end || start >= fileSize) {
            return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
                    .header(HttpHeaders.CONTENT_RANGE, "bytes */" + fileSize)
                    .build();
        }

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(end - start + 1);
        headers.setContentRange("bytes " + start + "-" + end + "/" + fileSize);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8));

        // method 1
        // 获取文件输入流资源
        InputStream inputStream = new FileInputStream(file);
        InputStreamResource resource = new InputStreamResource(inputStream);
//        // method 2
//        // 创建文件资源
//        Resource resource = new FileSystemResource(file);

        // 返回部分文件内容
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileSize)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(end - start + 1)
                .headers(headers)
                .body(resource);
    }
}

```

## Thymeleaf

### dependency

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

### a simple example

- put page in `src/main/resources/templates`
- put static resources in `src/main/resources/static`
- add `@Controller` and `@RequestMapping`, not need `@ResponseBody`
- the returned string is name of page file in `src/main/resources/templates`
- attribute passed to page:
    - `model.addAttribute(key, value)`
    - `model.addAllAttributes(map)`

```java

@Controller
public class MVCController {

    // thymeleaf test
    @RequestMapping(value = "login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "login.action")
    public String loginac(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        return "logined";
    }
}
```

```html
<!--logined.html-->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>Login Success</h1>
<h2 th:text="${password}"></h2>
<h2 th:id="${password}">password: [[${password}]]</h2>
</body>
</html>
```

### expression

- ${var}

```html
<p th:text="${message}"></p>
```

- *{var}

```html

<div th:object="${session.user}"><!-- bind session.user as context object by th:object -->
    <p th:text="*{name}"></p><!-- session.user.name -->
    <p th:text="*{sex}"></p>
    <p th:text="*{age}"></p>
</div>
```

- #{var}: for Localization
    - create file `messages_en.properties` and `messages_zh.properties` in `src/main/resources/i18n`
    - name of i18n config file:
        - basename.properties
        - basename_language.properties
        - basename_language_country.properties
    - add configuration class:
      ```java
      
      import java.util.Locale;
       
      import org.springframework.context.annotation.Bean;
      import org.springframework.context.annotation.Configuration;
      import org.springframework.web.servlet.LocaleResolver;
      import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
      import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
      import org.springframework.web.servlet.i18n.CookieLocaleResolver;
      import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
       
      /**
       * 国际化配置
       * 
       * @author YqZhilan
       *
       */
      @Configuration
      public class I18nConfig extends WebMvcConfigurerAdapter {
          @Bean
          public LocaleResolver localeResolver(){
              
              CookieLocaleResolver localeResolver = new CookieLocaleResolver();
              localeResolver.setCookieName("localeCookie"); // 将语言信息添加到Cookie中
              //设置默认区域
              localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE); // 默认简化汉语
              localeResolver.setCookieMaxAge(86400);//设置cookie有效期.24小时
              return localeResolver;
          }
          
          @Bean
          public LocaleChangeInterceptor localeChangeInterceptor() {
              LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
              // 参数名
              lci.setParamName("l");
              return lci;
          }
       
          @Override
          public void addInterceptors(InterceptorRegistry registry) {
              registry.addInterceptor(localeChangeInterceptor());
          }
      }
      ```
    - write localized message in properties file like:
        - `welcome.message = 欢迎` in `messages_zh.properties` and `welcome.message = Welcome` in
          `messages_en.properties`
        - `welcome.message = Welcome, {0}` in `messages_en.properties`, to format it:
          ```java
          import java.util.Locale;
          import java.util.ResourceBundle;
          
          public class Main {
             public static void main(String[] args) {
                 // 设置语言环境为英语
                 Locale locale = new Locale("en");
                 ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
          
                 // 获取消息并替换占位符
                 String welcomeMessage = messages.getString("welcome.message");
                 String formattedMessage = java.text.MessageFormat.format(welcomeMessage, "John");
                 
                 // ...
             }
          }
          ```
        - `<p th:text="#{welcome.user.message(${session.user.name})}"></p>`
    - create localization config in `src/main/resources/application.properties`:
      `spring.messages.basename=i18n/messages`
- @{url}

```html
<!--绝对地址示例：-->
<!-- https://fanlychie.github.io -->
<p th:text="@{https://fanlychie.github.io}"></p>

<!--页面相对地址示例：-->
<!-- commons/base.html -->
<p th:text="@{commons/base.html}"></p>

<!--上下文相对地址（相对于当前的服务）示例：-->
<!-- /css/mian.css -->
<p th:text="@{/css/mian.css}"></p>

<!--服务器相对地址（相对于部署在同一个服务器中的不同服务）示例：-->
<!-- /image/upload -->
<p th:text="@{~/image/upload}"></p>
```

- ~{...}:
    - ~{templatename} 引用整个模板文件的代码片段
    - ~{templatename :: selector} selector 可以是 th:fragment 指定的名称或其他选择器。 如类选择器、ID选择器等
    - ~{::selector} 相当于 ~{this :: selector}，表示引用当前模板定义的代码片段

### inner object

1. #ctx
2. #vars
3. #locale
4. #request
5. #response
6. #session
7. #servletContext

- !!! variable put into session could not be access by `#session.xxx`, should use `${session.xxx}`

```html
<!-- zh_CN -->
<p th:text="${#ctx.getLocale()}"></p>
<!-- Welcome to BeiJing! -->
<p th:text="${#ctx.getVariable('message')}"></p>
<!-- true -->
<p th:text="${#ctx.containsVariable('message')}"></p>

<!-- zh_CN -->
<p th:text="${#vars.getLocale()}"></p>
<!-- Welcome to BeiJing! -->
<p th:text="${#vars.getVariable('message')}"></p>
<!-- true -->
<p th:text="${#vars.containsVariable('message')}"></p>

<!-- zh_CN -->
<p th:text="${#locale}"></p>
<!-- CN -->
<p th:text="${#locale.country}"></p>
<!-- 中国 -->
<p th:text="${#locale.displayCountry}"></p>
<!-- zh -->
<p th:text="${#locale.language}"></p>
<!-- 中文 -->
<p th:text="${#locale.displayLanguage}"></p>
<!-- 中文 (中国) -->
<p th:text="${#locale.displayName}"></p>

<!-- 2BCB2A0EACFF2D9D249D9799431B5127 -->
<p th:text="${#session.id}"></p>
<!-- 1499786693244 -->
<p th:text="${#session.lastAccessedTime}"></p>
<!-- fanlychie -->
<p th:text="${#session.getAttribute('user').name}"></p>
```

### value

- `th:xxx=${var}` / `th:attr="xxx=${var}"`: change html attribute xxx to var
    - `th:text="${var}"`: change html attribute xxx to var
    - `th:utext="${var}"`: change html attribute xxx to var, but escape html (不进行字符转义)
    - `th:id="${var}"`
    - `th:value="${var}"`
    - `th:src="${var}"`
    - `th:href="${var}"`
    - `th:title="${var}"`
    - `th:alt="${var}"`
    - `th:checked="${var}"`
    - `th:disabled="${var}"`
    - `th:class="${var}"`
    - `th:style="${var}"`
    - `th:attr="xxx=${var}"`: change value of a attribute xxx (including custom attributes)
    - `<xx th:xx1-xx2="${var}" />` = `<xx th:xx1="${var}" th:xx2="${var}"/>`
    - `th:attrappend="xxx=${var}"` / `th:attrprepend="xxx=${var}"`: append / prepend var to attribute xxx
- calculate:
    - string concat:
        - `<p th:text="'Welcome to ' + ${location} + '!'"></p>`
        - `<p th:text="|Welcome to ${location}!|"></p>`
    - value calculate:
        - `<p th:text="${pagination.page + 1}"></p>` = `<p th:text="${pagination.page} + 1"></p>`
    - boolean calculate:
        - `<p th:text="${user.online and user.vip}"></p>`
        - `<p th:text="${user.online or user.vip}"></p>`
        - `<p th:text="${!user.online}"></p>`
        - `<p th:text="${not user.online}"></p>`
    - compare:
        - `<p th:text="${user.age < 60}"></p>`
        - `<p th:text="${user.age <= 60}"></p>`
        - `<p th:text="${user.age > 18}"></p>`
        - `<p th:text="${user.age >= 18}"></p>`
        - `<p th:text="${user.age == 18}"></p>`
        - `<p th:text="${user.age != 18}"></p>`
    - `<p th:text="${user.online ? '在线' : '离线'}"></p>`
    - `<p th:text="${token} ?: 'please login'"></p>` = `<p th:text="${token} ?: _">please login</p>`

### iterate

```html

<table border="1">
    <tr>
        <th>col</th>
        <th>username</th>
        <th>password</th>
        <th>email</th>
    </tr>
    <tr th:each="user, status : ${users}"><!-- status could be omitted -->
        <td th:text="${status.index}"></td>
        <td th:text="${user.username}"></td>
        <td th:text="${user.password}"></td>
        <td th:text="${user.email}"></td>
    </tr>
</table>
```

- properties of status:
    - index:`int` index of current element
    - count:`int` index of current element + 1
    - size:`int` size of list
    - even:`boolean` true if index is even
    - odd:`boolean` true if index is odd
    - first:`boolean` true if index is 0
    - last:`boolean` true if index is size - 1
    - current:`Object` current element

### conditional

- `th:if="xxx"`
    - if xxx is true, show element
- `th:unless="xxx"`
    - show element if xxx is false (unless xxx is true) / not show element only if xxx is true
- `th:if="xxx" th:else="yyy"`
    - if xxx is true show element
    - else show yyy
- `th:if="xxx" th:elseif="yyy" th:else="zzz"`
    - if xxx is true, show element
    - else if yyy is true, show yyy
    - else show zzz
- `th:switch`:
    ```html
    <div th:switch="${user.role}">
      <p th:case="admin">管理员</p>
      <p th:case="user">普通用户</p>
    </div>
    ```

### local variable

- `th:with="xxx=${var}"`: create a new variable xxx, the created variable can be used in the same scope (including child
  elements)

```html
<p th:with="name='${var}'">
    <span th:text="${name}"></span>
</p>
```

### inline expression

- `[[${var]]` = `th:text="${var}"`
    - `<h2>password: [[${password}]]</h2>`
- `[(${var}]` = `th:utext="${var}"`
    - `<h2>password: [(${password})]</h2>`
- `th:inline="xxx"`
    - `th:inline="none"`: disable inline expression
    - `th:inline="text"`: inline expression is text
    ```html
    <p th:inline="text">
    [# th:each="city : ${cities}"]
        [(${city.name})]
    [/]
    </p>
    ```
    - `th:inline="html"`: inline expression is html
    - `th:inline="javascript"`: inline expression is javascript
    ```html
    <script th:inline="javascript">
        var user = [[${user}]];
        alert("用户名：" + user.name);
    </script>
    ```
    - `th:inline="css"`: inline expression is css
    ```html
    <style th:inline="css">
        body {
            background-color:[[${bgColor}]];
        }
    </style>
    ```

### template fragment

1. `th:fragment`: 定义一个可重用的模板片段
    ```html
    <div th:fragment="header">
      <h1>My Website</h1>
      <p>Welcome to my website</p>
    </div>
    ```
2. `th:replace`: 用指定的片段替换当前元素
    ```html
    <!-- 在另一个模板中引用片段 -->
    <div th:replace="~{fragments/header :: header}">
      <!-- 这个 div 将被 header 片段替换 -->
    </div>
    ```
3. `th:insert`: 将指定的片段插入到当前元素内部
    ```html
    <!-- 在另一个模板中插入片段 -->
    <div th:insert="~{fragments/header :: header}">
      <!-- header 片段将被插入到这个 div 内部 -->
    </div>
    ```
4. `th:remove`: 根据条件移除元素
    ```html
    <!-- 移除整个元素及其内容 -->
    <div th:remove="all">
      This will be removed.
    </div>
    
    <!-- 移除元素的内容，但保留元素本身 -->
    <div th:remove="body">
      This content will be removed, but the div will remain.
    </div>
    
    <!-- 移除元素的开始和结束标签，但保留其内容 -->
    <div th:remove="tag">
      This content will remain, but the div tags will be removed.
    </div>
    
    <!--none：不做任何处理。该值对于动态计算时有用-->
    <div class="hint" th:remove="none">
        <p>none<span> ^</span></p>
    </div>

    <!--all-but-first：除第一个子项以外，删除其它所有子项-->
    <div class="hint" th:remove="all-but-first">
        <p>all-but-first1<span> %</span></p>
        <!-- 上面第一个子项不会被删除，从第二个子项开始全部会被删除-->
        <p>all-but-first2<span> %</span></p>
    </div>
    ```
    - apply of `th:remove`
    ```html
    <div th:remove="${isAdmin} ? all : none">
        <!-- 只有管理员才能看到这个内容 -->
        <p>Admin content here</p>
    </div>
    ```

# RESTful

- RESTful is a set of constraints that define a set of architectural principles for building web services.

## An Example of RESTful API

| Path                | Method | Description                   |
|---------------------|--------|-------------------------------|
| /users              | GET    | Get all users                 |
| /users/{id}         | GET    | Get user by id                |
| /users              | POST   | Create a new user             |
| /users              | PUT    | Update user by id             |
| /users/{id}         | DELETE | Delete user by id             |
| /users/{id}/friends | GET    | Get all friends of user by id |

# Spring Filter

- create a filter

```java
package com.li.hello_spring_practice1.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class MyHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("MyHandlerInterceptor preHandle");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("MyHandlerInterceptor postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("MyHandlerInterceptor afterCompletion");
    }
}
```

- register the filter to configuration class

```java
package com.li.hello_spring_practice1.config;

import com.li.hello_spring_practice1.interceptor.MyHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// method 1
/*@Configuration
public class MySpringMVCConfig {

    @Autowired
    MyHandlerInterceptor myHandlerInterceptor;
    @Bean
    WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(myHandlerInterceptor).addPathPatterns("/**");
            }
        };
    }
}*/

// method 2
@Configuration
public class MySpringMVCConfig implements WebMvcConfigurer {

    @Autowired
    MyHandlerInterceptor myHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myHandlerInterceptor).addPathPatterns("/**");
    }
}
```

- orders of multiple filters

```
MyHandlerInterceptor preHandle
MyHandlerInterceptor1 preHandle
MyHandlerInterceptor2 preHandle
MyHandlerInterceptor2 postHandle
MyHandlerInterceptor1 postHandle
MyHandlerInterceptor postHandle
MyHandlerInterceptor2 afterCompletion
MyHandlerInterceptor1 afterCompletion
MyHandlerInterceptor afterCompletion
```

- if one filter returns true in prehandle, it's afterCompletion will be called anyway
- if any filter returns false in prehandle:
    - all filters' postHandle could not be called
    - all the prehandle of filters after it will not be called
- if one postHandle returns false, all the postHandle of filters before it will not be called

# Spring Exception

## `@ExceptionHandler`

- declare a method to handle exception
- if `@ExceptionHandler` is declared in a controller, it will auto registered as the exception handler of the controller
- `@ExceptionHandler(xxx.class)`: `xxx` is the name of the exception class to be handled

### Example

```java
// EmployeeRestController.handleArithmeticException
@ExceptionHandler(ArithmeticException.class)
public Result handleArithmeticException(ArithmeticException e) {
    return new Result(500, e.getMessage());
}
```

## `@ControllerAdvice`

- declare a class to handle exception globally
- method decorated with `@ExceptionHandler` in this class will be registered as global exception handler
- if same type exception handled by inner controller method, the inner controller method will be called for having
  higher priority
- !!! if return json data, `@ResponseBody` should be added to the method or class

### Example

```java
package com.li.hello_spring_practice1.advice;

import com.li.hello_spring_practice1.bean.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ArithmeticException.class)
    public Result handleException(Exception e) {
        System.out.println("[GlobalExceptionHandler]: " + e.getMessage());
        return Result.error(500, e.getMessage());
    }
}
```

- if either `@ExceptionHandler` or `@ControllerAdvice` is declared in a controller, it will call the default exception
  handler of the controller and return 500 page or json to browser

## Exception handling in Project

- backend only concern the correct service logic
- backend throw exception and terminate service if in unexpected situation and return error message to frontend
- backend should inform services in higher level by throwing business exception if terminate current service
- frontend should concern response code and message

### a simple business exception class example

#### a enum class to store error code and message

```java

@Data
public enum BusinessError {

    ORDER_CLOSED(10001, "Order closed"),
    ORDER_NOT_EXIST(10002, "Order not exist"),
    ORDER_NOT_PAID(10003, "Order not paid"),
    ORDER_TIMEOUT(10004, "Order timeout")
    // ...
    ;

    private final int errorCode;
    private final String errorMessage;

    public int code() {
    }

    public String message() {
    }
}
```

#### a business exception class

```java
package xxxxxxx;

public class BusinessException extends RuntimeException {
    private int errorCode;
    private String errorMessage;

    public BusinessException(BusinessError businessError) {
        this.errorCode = businessError.code();
        this.errorMessage = businessError.message();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
```

#### register the exception handler

```java

@ExceptionHandler(BusinessException.class)
public Result handleBusinessException(BusinessException e) {
    return new Result(e.getErrorCode(), e.getErrorMessage());
}
```

#### throw the exception in service

```java

@getMapping("/{orderId}")
public void handle11(int id) {
    if (id < 0) {
        throw new BusinessException(BusinessError.ORDER_NOT_EXIST);
    }
    // ...
}
```

# Spring Data Validation

- import spring-boot-stater-validation
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    ```
- add valid condition decorators to beans
    ```java
    package com.li.hello_spring_practice1.bean;
    
    import jakarta.validation.constraints.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    
    import java.math.BigDecimal;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Employee {
        /*
        for sql table:
        CREATE TABLE IF NOT EXISTS `employee`
        (
            `id`      INT(11)        NOT NULL AUTO_INCREMENT,
            `name`    VARCHAR(255)   NOT NULL,
            `age`     INT(11)        NOT NULL,
            `email`   VARCHAR(255)   NOT NULL,
            `gender`  INT1           NOT NULL,
            `address` VARCHAR(255)   NOT NULL,
            `salary`  DECIMAL(10, 2) NOT NULL,
            PRIMARY KEY (`id`) USING BTREE
        );
         */
        private Integer id;
    
        @NotBlank
        private String name;
    
        @Min(value = 0, message = "age could smaller than 0")
        private Integer age;
    
        @Email(message = "email format error")
        private String email;
    
        @Max(value = 1, message = "gender could only be 0 or 1")
        @Min(value = 0, message = "gender could only be 0 or 1")
        private Integer gender;
    
        @NotBlank
        private String address;
    
        @DecimalMin(value = "0.00", message = "salary could not smaller than 0")
        private BigDecimal salary;
    }
    ```
- add `@Valid` to the parameter to be validated in controller method
    ```java
    @RequestMapping(value = "/employee/valtest", method = RequestMethod.GET)
    public Result getEmployeeValTest(
            @RequestBody @Valid Employee employee
    ) {
        return Result.ok();
    }
    ```

- JSR 303/349 (Bean Validation) 标准注解
    - `@Null`：验证对象是否为 null
    - `@NotNull`：验证对象是否不为 null
    - `@AssertTrue`：验证 boolean 属性是否为 true
    - `@AssertFalse`：验证 boolean 属性是否为 false
    - `@Min(value)`：验证数值是否大于等于指定的最小值
    - `@Max(value)`：验证数值是否小于等于指定的最大值
    - `@DecimalMin(value)`：验证字符串或数值是否大于等于指定的最小值（支持小数）
    - `@DecimalMax(value)`：验证字符串或数值是否小于等于指定的最大值（支持小数）
    - `@Size(min, max)`：验证集合、数组、字符串长度是否在指定范围内
    - `@Digits(integer, fraction)`：验证字符串是否是符合指定格式的数字（整数位数和小数位数）
    - `@Past`：验证日期是否在过去
    - `@Future`：验证日期是否在未来
    - `@Pattern(regex)`：验证字符串是否符合指定的正则表达式
- Hibernate Validator 扩展注解
    - `@NotEmpty`：验证集合、数组、字符串是否不为空（但可以接受空白字符串）
    - `@NotBlank`：验证字符串是否不为空且不是空白字符
    - `@Email`：验证字符串是否为有效的电子邮件地址
    - `@URL`：验证字符串是否为有效的URL
- 自定义注解
    - 可以通过实现 ConstraintValidator 接口来自定义校验逻辑，并创建自己的校验注解。

- `@NotNull` & `@NotEmpty`: `@NotNull` could be empty string ("") but `@NotEmpty` could not
- `@NotEmpty` & `@NotBlank`:
    - `@NotEmpty`: "not null and size > 0" whick could decorate collection, array, map, string
    - `@NotBlank`: could only decorate string

## `BindingResult`: get errors of `@Valid`

```java

@RequestMapping(value = "/employee/valtest", method = RequestMethod.GET)
public Result getEmployeeValTest(
        @RequestBody @Valid Employee employee,
        BindingResult bindingResult
) {
    if (!bindingResult.hasErrors())
        return Result.ok(employee);

    Map<String, String> errorMap = new HashMap<>();
    for (var fieldError : bindingResult.getFieldErrors())
        errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
    return new Result(500, "bad parameters", errorMap);
}
```

## global exception handler for `@Valid`

- exception class of `@Valid`: `MethodArgumentNotValidException`
- use `MethodArgumentNotValidException.getBindingResult()` to get `BindingResult`

```java

@ExceptionHandler(MethodArgumentNotValidException.class)
public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    Map<String, String> errorMap = new HashMap<>();
    for (var fieldError : e.getBindingResult().getFieldErrors())
        errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
    return new Result(500, "bad parameters", errorMap);
}
```

## Custom Validation

- implement `ConstraintValidator<TargetAnnotationName, TargetTypeToBeValidated>`
    ```java
    package com.li.hello_spring_practice1.validator;
    
    import com.li.hello_spring_practice1.annotation.Gender;
    import jakarta.validation.ConstraintValidator;
    import jakarta.validation.ConstraintValidatorContext;
    
    public class GenderValidator implements ConstraintValidator<Gender, Integer> {
    
        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
            return value == 1 || value == 0;
        }
    }
    ```

- create annotation and bind the validator

    ```java
    package com.li.hello_spring_practice1.annotation;
    
    import com.li.hello_spring_practice1.validator.GenderValidator;
    import jakarta.validation.Constraint;
    import jakarta.validation.Payload;
    
    import java.lang.annotation.Documented;
    import java.lang.annotation.ElementType;
    import java.lang.annotation.Retention;
    import java.lang.annotation.RetentionPolicy;
    import java.lang.annotation.Target;
    
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Constraint(validatedBy = {GenderValidator.class})
    public @interface Gender {
        String message() default "{jakarta.validation.constraints.NotNull.message}";
    
        Class<?>[] groups() default {};
    
        Class<? extends Payload>[] payload() default {};
    }
    
    ```

- use annotation to valid variable
    ```java
    public class Employee {
        // ...
        @Gender(message = "gender must be 0 or 1")
        private Integer gender;
        // ...
    ```
# Internationalization (i18n)
- add properties:
  - messages.properties
  - messages_zh.properties
  - messages_ja.properties
  - messages_en.properties
- set `spring.messages.basename=messages`
- use i18n as `@Gender(message = "{gender.invalid.message}")`, `gender.invalid.message` is defined in messages_xx.properties
- returned message will match “Accept-Language” in request header