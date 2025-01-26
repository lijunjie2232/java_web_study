package com.li.hellospringbootbase1.event;

public class UserSigninEvent {
    private String name;

    public UserSigninEvent(String name) {
        this.name = name;
    }
}
