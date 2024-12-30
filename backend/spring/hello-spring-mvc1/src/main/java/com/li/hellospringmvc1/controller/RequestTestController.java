package com.li.hellospringmvc1.controller;

import com.li.hellospringmvc1.bean.Handle06Form;
import com.li.hellospringmvc1.bean.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class RequestTestController {

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

    @RequestMapping(value = "handle07")
//    receive json data
    public String handle07(@RequestBody Handle06Form form) {
        System.out.println(form);
        return "{\"msg\": \"ok\"}";
    }
}
