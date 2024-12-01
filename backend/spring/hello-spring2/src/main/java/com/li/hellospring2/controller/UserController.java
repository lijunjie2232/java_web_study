package com.li.hellospring2.controller;

import com.li.hellospring2.bean.Person;
import com.li.hellospring2.service.UserService;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@ToString
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired(required = false)
    Person PFactory;

    @Autowired
    Person person;

    @Autowired
    Person personli;

    @Qualifier("personli")
    @Autowired
    Person li;

    @Autowired
    List<Person> personList;

    @Autowired
    Map<String, Person> personMap;

    @Autowired
    ApplicationContext applicationContext;

    public UserController(UserService userService) {
        System.out.println("UserController constructor");
        System.out.println(userService);
    }

    Person person2;

    @Autowired
    @Qualifier("personli")
    public void SetPerson2(Person personli){
        System.out.println("SetPerson2");
        System.out.println(personli);
        this.person2 = personli;
    }
}
