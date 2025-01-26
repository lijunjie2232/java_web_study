package com.li.hellospringbootbase1.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class UserSigninEvent  {
    private String message;

    public UserSigninEvent(String message) {
        this.message = message;
    }

}