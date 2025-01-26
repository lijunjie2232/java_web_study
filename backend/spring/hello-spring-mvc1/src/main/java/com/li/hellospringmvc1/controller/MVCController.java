package com.li.hellospringmvc1.controller;

import com.li.hellospringmvc1.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MVCController {

    // thymeleaf test
    @RequestMapping(value = "login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "login.action")
    public String loginac(@RequestParam("username") String username, @RequestParam("password") String password, Model model){
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        List<User> userList = new ArrayList<>();
        userList.add(new User("1", "123", "123@qq.com"));
        userList.add(new User("2", "321", "321@qq.com"));
        userList.add(new User("3", "111", "111@qq.com"));
        model.addAttribute("users", userList);
        return "logined";
    }
}
