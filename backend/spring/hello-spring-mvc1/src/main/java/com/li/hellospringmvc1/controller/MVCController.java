package com.li.hellospringmvc1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "logined";
    }
}
