package com.li.hellospringbootbase1.controller;

import com.li.hellospringbootbase1.event.UserSigninEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v3/api")
@RestController
public class UserController {


    @Autowired
    ApplicationEventPublisher publisher;

    @RequestMapping("/signin")
    public String signin(@RequestParam("username") String username){
        publisher.publishEvent(new UserSigninEvent(username+" 用户登录"));

        return "{\"message\": \"success\", \"code\": 200}";
    }

}
