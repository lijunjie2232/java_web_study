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
- 添加响应头：`.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)`, 设置Content-Disposition为attachment;filename=filename，提示浏览器以附件形式下载文件，并指定文件名。
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
   - 使用`.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8))`设置响应头的Content-Disposition为附件形式下载，并指定文件名
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

