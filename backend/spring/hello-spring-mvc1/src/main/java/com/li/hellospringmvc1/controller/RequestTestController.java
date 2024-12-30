package com.li.hellospringmvc1.controller;

import com.li.hellospringmvc1.bean.Handle06Form;
import com.li.hellospringmvc1.bean.User;
import com.li.hellospringmvc1.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@RestController
public class RequestTestController {

    // get tmp dir path from spring boot application.properties, default is ./tmp
    @Value("${tmp.path}")
    private File tmpPath;


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
        for (MultipartFile multipartFile : files) {
            System.out.println(multipartFile.getOriginalFilename());
            FileUtil.multipartFileWriter(multipartFile, tmpPath, true);
        }
        return "{\"msg\": \"ok\"}";
    }

}
