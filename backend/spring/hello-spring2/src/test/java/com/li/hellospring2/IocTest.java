package com.li.hellospring2;

import com.li.hellospring2.bean.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IocTest {

    @Autowired
    User user;

    @Test
    public void userTest(){
        System.out.println(user);
    }
}
