package com.li.hellospringmvc1.controller;

import com.li.hellospringmvc1.bean.Handle06Form;
import com.li.hellospringmvc1.bean.User;
import com.li.hellospringmvc1.util.FileUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
public class RequestTestController {

    // get tmp dir path from spring boot application.properties, default is ./tmp
    @Value("${tmp.path}")
    private File tmpPath;


    // ------------------ SpringMVC Request Test ------------------
    @RequestMapping("handle01")
    public String handle01(String username, String password, boolean accept) {
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        System.out.println("accept = " + accept);
        return "{\"msg\": \"ok\"}";
    }

    @RequestMapping("handle02")
    public String handle02(@RequestParam("username") String user, @RequestParam(defaultValue = "") String password, @RequestParam(value = "agree", required = false) boolean accept) {
        System.out.println("username = " + user);
        System.out.println("password = " + password);
        System.out.println("accept = " + accept);
        return "{\"msg\": \"ok\"}";
    }

    @RequestMapping(value = "handle03")
    public String handle03(User user) {
        System.out.println(user);
        return "{\"msg\": \"ok\"}";
    }


    @RequestMapping(value = "handle04")
    public String handle04(@RequestHeader("host") String host, @RequestHeader("user-agent") String userAgent) {
        System.out.println(host);
        System.out.println(userAgent);
        return "{\"msg\": \"ok\"}";
    }

    @RequestMapping(value = "handle05")
    public String handle05(@CookieValue("JSESSIONID") String sessionId) {
        System.out.println(sessionId);
        return "{\"msg\": \"ok\"}";
    }

    @RequestMapping(value = "handle06")
    public String handle06(Handle06Form form) {
        System.out.println("-------- form --------");
        System.out.println(form);
        System.out.println("-------- form --------");
        return "{\"msg\": \"ok\"}";
    }

    //    receive json data
    @RequestMapping(value = "handle07")
    public String handle07(@RequestBody Handle06Form form) {
        System.out.println(form);
        return "{\"msg\": \"ok\"}";
    }

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
        if (files.length == 0)
            for (MultipartFile multipartFile : files) {
                System.out.println(multipartFile.getOriginalFilename());
                FileUtil.multipartFileWriter(multipartFile, tmpPath, true);
            }
        return "{\"msg\": \"ok\"}";
    }

    @RequestMapping(value = "handle09")
    public String handle09(
//            HttpEntity<String> entity
            HttpEntity<Handle06Form> entity
    ) {
        System.out.println(entity.getBody());
        System.out.println(entity.getHeaders());
        return "{\"msg\": \"ok\"}";
    }

    // Servlet API Test
    @RequestMapping(value = "handle10")
    public void handle10(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // print json request body
        System.out.println(request.getReader().lines().reduce("", (acc, cur) -> acc + cur));
        response.getWriter().write("{\"msg\": \"ok\"}");
    }

    // ------------------ SpringMVC Response Test ------------------
    // return json data
    @RequestMapping(value = "handle11")
    public User handle11() {
        return new User("123", "321", "123@321.123");
    }

    // file download
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
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment;filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8)
                    )
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(bytes.length)
                    .body(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
