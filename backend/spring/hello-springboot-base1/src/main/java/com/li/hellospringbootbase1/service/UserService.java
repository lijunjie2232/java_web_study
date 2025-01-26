package com.li.hellospringbootbase1.service;

import com.li.hellospringbootbase1.event.UserSigninEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Async
    @EventListener(UserSigninEvent.class)
    public void signin(UserSigninEvent event) {
        log.info("[user signin event]: "+ event.getMessage());
    }
}
