package com.li.hellospringmvc1.controller;

import com.li.hellospringmvc1.bean.User;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public String handle02(
            @RequestParam("username") String user,
            @RequestParam(defaultValue = "") String password,
            @RequestParam(value = "agree", required = false) boolean accept) {
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
    public String handle04(
            @RequestHeader("host") String host,
            @RequestHeader("user-agent") String userAgent
    ) {
        System.out.println(host);
        System.out.println(userAgent);
        return "{\"msg\": \"ok\"}";
    }
}
